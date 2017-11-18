package Data;

import java.sql.*;
import java.util.Properties;

public class Database {

    private String userName = "memeagram";
    private String password = "QypETRTfeuQBkeoC";
    private String serverName = "159.203.66.32";
    private String portNumber = "3306";
    private String dbName = "dbo";

    Connection conn;

    public Database() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);

        conn = DriverManager.getConnection(
                "jdbc:mysql://" + serverName + ":" + portNumber + "/" + dbName, connectionProps);
    }

    public void GetUsers() throws SQLException {
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("SELECT Id, UserName, FirstName, LastName FROM Users");

        while (rs.next()) {
            int id = rs.getInt("Id");
            String userName = rs.getString("UserName");
            String firstName = rs.getString("FirstName");
            String lastName = rs.getString("LastName");
            System.out.println(id);
            System.out.println(userName);
            System.out.println(firstName);
            System.out.println(lastName);
        }
    }
}


//package DB;
//
//import javax.imageio.stream.FileImageInputStream;
//import java.awt.image.BufferedImage;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.sql.*;
//import java.util.Properties;
//
//public class Database {
//
//    private String userName = "memeagram";
//    private String password = "QypETRTfeuQBkeoC";
//    private String serverName = "159.203.66.32";
//    private String portNumber = "3306";
//    private String dbName = "dbo";
//
//    Connection conn;
//
//    public Database() throws SQLException {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        Properties connectionProps = new Properties();
//        connectionProps.put("user", userName);
//        connectionProps.put("password", password);
//
//        conn = DriverManager.getConnection(
//                "jdbc:mysql://" + serverName + ":" + portNumber + "/" + dbName, connectionProps);
//    }
//
//    public void GetUsers() throws SQLException {
//        Statement s = conn.createStatement();
//        ResultSet rs = s.executeQuery("SELECT Id, UserName, FirstName, LastName FROM Users");
//
//        while (rs.next()) {
//            int id = rs.getInt("Id");
//            String userName = rs.getString("UserName");
//            String firstName = rs.getString("FirstName");
//            String lastName = rs.getString("LastName");
//            System.out.println(id);
//            System.out.println(userName);
//            System.out.println(firstName);
//            System.out.println(lastName);
//        }
//    }
//
//    /**
//     * Method getUser selects the UserID
//     * from Users Table where UserName = @userName
//     *
//     * @param userName
//     * @throws SQLException
//     *
//     */
//    public int getUserId(String userName) throws SQLException
//    {
//        String stmt = "SELECT Id FROM Users WHERE UserName = ?";
//        PreparedStatement preparedStatement = conn.prepareStatement(stmt);
//        preparedStatement.setString(1,userName);
//        int id = preparedStatement.executeUpdate();
//
//        return id;
//
//    }
//
//    /**
//     * Method insertMeme to insert new Meme into the Database.
//     *
//     * @param imgURL
//     * @param captionText
//     * @param mainCategory
//     * @param subCategory
//     * @param userName //added for now until we get logging in set up
//     */
//    public void insertMeme(String imgURL, String captionText, String mainCategory, String subCategory, String userName) throws SQLException, IOException, FileNotFoundException
//    {
//
//        // Code to upload image to dropbox https://github.com/dropbox/dropbox-sdk-java#dropbox-for-java-tutorial
//        /* try(InputStream in = new FileInputStream(imgURL)) {
//              FileMetadata metadata = client.files().uploadBuilder("/"+imgURL).uploadAndFinish(in);
//         }*/
//        int userId = getUserId(userName);
//
//        String stmt = "Call sp_insertMeme(?,?,?,?,?)";
//        PreparedStatement preparedStmt = conn.prepareStatement(stmt);
//        preparedStmt.setString(1,imgURL);
//        preparedStmt.setString(2,captionText);
//        preparedStmt.setString(3,mainCategory);
//        preparedStmt.setString(4,subCategory);
//        preparedStmt.setInt(5,userID);
//        preparedStmt.execute();
//    }
//
//    /**
//     * Method voteOnMeme adds a vote to the HoldingTank Table.
//     * Once the votes is 50 or greater, the voted on meme is then
//     * removed from the HoldingTank and added to the Meme Table
//     *
//     * @param memeId
//     *
//     */
//
//    public void voteToAddMemeToCollection(int memeId) throws SQLException
//    {
//
//        String stmt = "Call sp_voteOnMeme(?)";
//        PreparedStatement preparedStmt = conn.prepareStatement(stmt);
//        preparedStmt.setInt(1,memId);
//        preparedStmt.execute();
//    }
//
//    /**
//     * Need to create writeUser Method
//     */
//
//
//    /**
//     * Need to create Select Category Meme after integrating with DropBox
//     */
//}
