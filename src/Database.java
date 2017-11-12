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
