package memeagram.data.objects;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import memeagram.Context;
import memeagram.data.DatabaseAccessController;
import memeagram.data.DropBoxController;
import com.mysql.jdbc.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

public class Meme {
    public Integer id;
    public BufferedImage originalImage;
    public BufferedImage memeImage;
    public ArrayList<String> tags;
    public int userId;
    public String url;
    public String captionText;

    private DatabaseAccessController Dac;
    private DropBoxController Dbc;

    public Meme(Context context){
        tags = new ArrayList<>();
        Dac = context.dac;
        Dbc = context.dbc;
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
}
