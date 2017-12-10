package memeagram.data.objects;

import memeagram.Context;
import memeagram.data.DatabaseAccessController;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    public int id;
    public String userName;
    public String firstName;
    public String lastName;

    DatabaseAccessController Dac;

    private User (Context context) {
        Dac = context.dac;
    }
    public User(Context context, String userName) throws SQLException {
        this(context);
        this.userName = userName;
    }

    public User(Context context, String userName, String firstName, String lastName) throws SQLException {
        this(context, userName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(Context context, int id, String userName, String firstName, String lastName) throws SQLException {
        this(context, userName, firstName, lastName);
        this.id = id;
    }

    public boolean create() throws SQLException {
        boolean userCreated = insertUser();
        if (userCreated) {
            id = getUserId(userName);
        }
        return userCreated;
    }

    public static User getUser(Context context, String _userName) throws SQLException {

        DatabaseAccessController Dac = context.dac;

        String stmt = "SELECT Id, UserName, FirstName, LastName FROM Users WHERE UserName = ?;";
        PreparedStatement preparedStatement = Dac.conn.prepareStatement(stmt);
        preparedStatement.setString(1,_userName);
        ResultSet rs = preparedStatement.executeQuery();

        if (rs.next()) {
            User user = new User(context);
            user.id = rs.getInt("Id");
            user.userName = rs.getString("UserName");
            user.firstName = rs.getString("FirstName");
            user.lastName = rs.getString("LastName");

            return user;
        }
        return null;
    }

    private Integer getUserId(String userName) throws SQLException
    {
        String stmt = "SELECT Id FROM Users WHERE UserName = ?;";
        PreparedStatement preparedStatement = Dac.conn.prepareStatement(stmt);
        preparedStatement.setString(1,userName);
        return preparedStatement.executeUpdate();
    }

    private boolean insertUser() throws SQLException {
        String stmt = "INSERT INTO Users(UserName, FirstName, LastName) VALUES(?,?,?)";
        PreparedStatement preparedStmt = Dac.conn.prepareStatement(stmt);
        preparedStmt.setString(1,userName);
        preparedStmt.setString(2,firstName);
        preparedStmt.setString(3,lastName);

        return preparedStmt.execute();
    }
}
