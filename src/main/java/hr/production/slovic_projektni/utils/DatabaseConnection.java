package hr.production.slovic_projektni.utils;


import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    //private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

    private static final String DATABASE_FILE = "conf/database.properties";

    public static Connection connectToDatabase() throws SQLException, IOException {
        Properties svojstva = new Properties();
        svojstva.load(new FileReader(DATABASE_FILE));
        String databaseUrl = svojstva.getProperty("databaseUrl");
        String username = svojstva.getProperty("username");
        String password = svojstva.getProperty("password");
        Connection veza = DriverManager.getConnection(databaseUrl,
                username,password);
        return veza;
    }
}
