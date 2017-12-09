package memeagram.data.objects;

import com.dropbox.core.DbxException;
import com.dropbox.core.v1.DbxEntry;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import memeagram.Context;
import memeagram.data.DatabaseAccessController;
import memeagram.data.DropBoxController;
import com.mysql.jdbc.StringUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.sql.RowSet;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

public class Meme {
    public Integer id;
    public BufferedImage originalImage;
    public BufferedImage memeImage;
    public ArrayList<String> tags;
    public int userId;
    public String url;
    public String captionText;
    public int numLikes;
    public int numDislikes;

    private DatabaseAccessController Dac;
    private DropBoxController Dbc;

    public Meme(Context context){
        tags = new ArrayList<>();
        Dac = context.dac;
        Dbc = context.dbc;
    }

    public boolean getImage() {
        if (StringUtils.isNullOrEmpty(url)) {
            return false;
        }

        try {
            URL url = new URL(this.url);
            memeImage = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (memeImage != null) {
            return true;
        }
        return false;
    }

    /*public BufferedImage returnImage(String stringURL) throws DbxException, IOException {

        
        DbxClientV2 dropbox = Dbc.client;
        File file;

        dropbox.files()
                .download(stringURL)
                .getInputStream()
                .close();


    }*/

    public static Meme getMemeById(Context context, int id) throws SQLException {
        DatabaseAccessController Dac = context.dac;

        String stmt = "SELECT Id, UserId, ImageUrl, CaptionText FROM Memes WHERE Id = ?;";
        PreparedStatement preparedStatement = Dac.conn.prepareStatement(stmt);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();

        Meme meme = new Meme(context);

        if (rs.next()) {
            meme.id = rs.getInt("Id");
            meme.userId = rs.getInt("UserId");
            meme.url = rs.getString("ImageUrl");
            meme.captionText = rs.getString("CaptionText");

        }
        else return null;

        meme.tags = getMemeTags(context, meme.id);

        return meme;
    }

    public boolean saveMeme() {
        DbxClientV2 dropbox = Dbc.client;

        // convert meme to temp file
        File file;
        try {
            file = File.createTempFile(UUID.randomUUID().toString(), ".jpg");
            ImageIO.write(memeImage, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


        // store temp file to dropbox and get direct url
        String dropboxPath = String.format("/memes/%s", file.getName());
        try(InputStream in = new FileInputStream(file.getAbsolutePath())) {
            dropbox.files()
                    .uploadBuilder(dropboxPath)
                    .uploadAndFinish(in);

            SharedLinkMetadata shareMeta = dropbox.sharing()
                    .createSharedLinkWithSettings(dropboxPath);

            url = shareMeta.getUrl();

            // alter query param to notify that we want to download the image
            url = url.substring(0,url.lastIndexOf("?"));
            url += "?dl=1";

        } catch (IOException | DbxException e) {
            e.printStackTrace();
            return false;
        }

        file.delete();

        // store meme meta to DB
        if (!StringUtils.isNullOrEmpty(url)) {
            try {
                id = insertMeme(userId, url, captionText);
                if (id == null){
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        // store meme tags to DB
        try {
            insertMemeTags(id, tags);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // success
        return true;
    }

    private static ArrayList<String> getMemeTags(Context context, int memeId) throws SQLException {
        DatabaseAccessController Dac = context.dac;

        String stmt = "SELECT TagText FROM MemeTags WHERE MemeId = ?;";
        PreparedStatement preparedStatement = Dac.conn.prepareStatement(stmt);
        preparedStatement.setInt(1, memeId);
        ResultSet rs = preparedStatement.executeQuery();

        ArrayList<String> tags = new ArrayList<>();

        while (rs.next()) {
            tags.add(rs.getString("TagText"));
        }

        return tags;
    }

    private Integer insertMeme(int userId, String url, String captionText) throws SQLException
    {
        String stmt = "INSERT INTO Memes(UserId, ImageUrl, CaptionText) VALUES(?,?,?);";

        PreparedStatement preparedStmt = Dac.conn.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
        preparedStmt.setInt(1,userId);
        preparedStmt.setString(2,url);
        preparedStmt.setString(3,captionText);
        preparedStmt.execute();

        try (ResultSet generatedKeys = preparedStmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        }
        return null;
    }

    private void insertMemeTags(int memeId, ArrayList<String> tags) throws SQLException
    {
        for (String tag: tags) {
            String stmt = "INSERT INTO MemeTags(MemeId, TagText) VALUES(?,?)";
            PreparedStatement preparedStmt = Dac.conn.prepareStatement(stmt);
            preparedStmt.setInt(1,memeId);
            preparedStmt.setString(2,tag);
            preparedStmt.execute();
        }

    }

    /*
        Method getAllMemes returns all memes from the Database
     */
    public static ArrayList<Meme> getAllMemes(Context context) throws SQLException
    {
        DatabaseAccessController Dac = context.dac;

        String statement = "SELECT Id, ImageUrl ,CaptionText,GROUP_CONCAT(TagText SEPARATOR ', ') TagText FROM dbo.Memes Memes LEFT JOIN dbo.MemeTags Tags ON Memes.Id=Tags.MemeId GROUP BY Memes.Id";
        PreparedStatement preparedStmt = Dac.conn.prepareStatement(statement);
        ResultSet rs = preparedStmt.executeQuery();

        ArrayList<Meme>  returnedMemes = new ArrayList<>();

        while (rs.next())
        {
            Meme meme = new Meme(context);
            meme.id = rs.getInt("Id");
            meme.userId = rs.getInt("UserId");
            meme.url = rs.getString("ImageUrl");
            meme.captionText = rs.getString("CaptionText");
            String str = rs.getString("TagText");
            String[] arr = str.split(",");
            Collections.addAll(meme.tags, arr);
            returnedMemes.add(meme);
        }
        return returnedMemes;
    }

    public static ArrayList<Meme> getMemesByTag(Context context, String tag) throws SQLException
    {
        DatabaseAccessController Dac = context.dac;

        String statement = "SELECT Id ,ImageUrl,CaptionText ,SUM(likes.IsLike=1) numLikes ,SUM(likes.IsLike=0) numDislikes FROM dbo.Memes memes LEFT JOIN dbo.MemeTags tags ON tags.MemeId=memes.Id LEFT JOIN dbo.Likes likes ON likes.MemeId=memes.Id WHERE tags.TagText = ? GROUP BY Id";
        PreparedStatement preparedStatement = Dac.conn.prepareStatement(statement);
        preparedStatement.setString(1,tag);
        ResultSet rs = preparedStatement.executeQuery();

        ArrayList<Meme> returnedMemes = new ArrayList<>();
        while (rs.next())
        {
            Meme meme = new Meme(context);
            meme.id = rs.getInt("Id");
            meme.url = rs.getString("ImageUrl");
            meme.captionText = rs.getString("CaptionText");
            meme.numLikes = rs.getInt("numLikes");
            meme.numDislikes = rs.getInt("numDislikes");
            //meme.memeImage = getMemeImage(meme.url);
            returnedMemes.add(meme);
        }
        return returnedMemes;
    }
    /**
     * You can also do something like this to sort,
     *
     * ArrayList memes = Memes.getMemesByTag(context,tag);
     * memes.sort(Comparator.comparing(Meme::getNumLikes).thenComparing(Meme::getTag));
     *
     * we would just have to create getter methods
     */

    /**
     * Comparator used to sort an ArrayList of Memes by Number of Likes in Ascending Order
     */
    public static Comparator<Meme> SORT_LIKES_ASCENDING = (one, other) -> one.numLikes < other.numLikes ? 1: one.numLikes > other.numLikes ? -1: 0;

    /**
     * Comparator used to sort an ArrayList of Memes by Number of Likes in Descending Order
     */
    public static Comparator<Meme> SORT_LIKES_DESCENDING = (one, other) -> one.numLikes > other.numLikes ? 1: one.numLikes < other.numLikes ? -1: 0;

    /**
     * Comparator used to sort an ArrayList of Memes by Number of Dislikes in Ascending Order
     */
    public static Comparator<Meme> SORT_DISLIKES_ASCENDING = (one,other) -> one.numDislikes < other.numDislikes ? 1: one.numDislikes > other.numDislikes ? -1: 0;

    /**
     * Comparator used to sort an ArrayList of Memes by Number of Dislikes in Descending Order
     */
    public static Comparator<Meme> SORT_DISLIKES_DESCENDING = (one,other) -> one.numDislikes > other.numDislikes ? 1: one.numDislikes < other.numDislikes ? -1: 0;

}
