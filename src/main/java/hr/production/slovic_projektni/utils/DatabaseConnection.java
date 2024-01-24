package hr.production.slovic_projektni.utils;


import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseConnection {
    //private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

    private static final String DATABASE_FILE = "db/database.properties";
    private static final String DATABASE_FILE2 = "conf/database.properties";


    public static Connection connectToDatabase() throws SQLException, IOException {

        Properties svojstva = new Properties();
        svojstva.load(new FileReader(DATABASE_FILE2));
        String databaseUrl = svojstva.getProperty("databaseUrl");
        String username = svojstva.getProperty("username");
        String password = svojstva.getProperty("password");
        Connection connection = DriverManager.getConnection(databaseUrl,
                username,password);
        return connection;
    }









}
