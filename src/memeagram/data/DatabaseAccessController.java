package memeagram.data;

import java.sql.*;
import java.util.Properties;

public class DatabaseAccessController {


    private String userName = "memeagram";
    private String password = "QypETRTfeuQBkeoC";
    private String serverName = "159.203.66.32";
    private String portNumber = "3306";
    private String dbName = "dbo";

    public Connection conn;

    public DatabaseAccessController() throws SQLException {
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
}
