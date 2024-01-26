package hr.production.slovic_projektni.utils;

import hr.production.slovic_projektni.model.User;
import hr.production.slovic_projektni.model.UserRole;


import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseUtilUsers {

   // private static final Logger logger = LoggerFactory.getLogger(DatabaseUtilUsers.class);


    public static void saveUser(User newUser){
        try(Connection connection = DatabaseConnection.connectToDatabase()){
            String insertUserSql = "INSERT INTO USERS (FIRST_NAME, LAST_NAME, USER_ROLE, USERNAME, PASSWORD_HASHED ) " +
                    "VALUES(?, ?, ?, ?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(insertUserSql);
            pstmt.setString(1, newUser.getFirstName());
            pstmt.setString(2, newUser.getLastName());
            pstmt.setString(3, newUser.getUserRole().getName().toUpperCase());
            pstmt.setString(4, newUser.getUsername());
            pstmt.setString(5, newUser.getPasswordHash());
            pstmt.execute();

        } catch (SQLException | IOException ex){
            ex.printStackTrace();
        }
    }



    public static Long nextUserId(){
        Long maxId = Long.parseLong("999");
        try(Connection connection = DatabaseConnection.connectToDatabase()) {
            String sqlQuery = "SELECT ID\n" +
                    "FROM USERS  \n" +
                    "WHERE ID = (SELECT MAX(ID) FROM USERS);";
            Statement stmt = connection.createStatement();
            stmt.execute(sqlQuery);
            ResultSet resultSet = stmt.getResultSet();

            resultSet.next();
            maxId = resultSet.getLong("ID");

        } catch (SQLException | IOException ex) {
            throw new RuntimeException(ex);
        }
        return maxId+1;
    }

    public static User activeUser(User user) {
        Long id;
        String firstName, lastName;
        UserRole userRole;
        try (Connection connection = DatabaseConnection.connectToDatabase()) {
            String sqlQuery = "SELECT ID, FIRST_NAME, LAST_NAME, USER_ROLE\n" +
                    "FROM USERS  \n" +
                    "WHERE USERNAME = ?;";

            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setString(1, user.getUsername());
            pstmt.executeQuery();
            ResultSet resultSet = pstmt.getResultSet();

            resultSet.next();
            id = resultSet.getLong("ID");
            userRole = UserRole.valueOf(resultSet.getString("USER_ROLE").toUpperCase());
            firstName = resultSet.getString("FIRST_NAME");
            lastName = resultSet.getString("LAST_NAME");

        } catch (SQLException | IOException ex) {
            throw new RuntimeException(ex);
        }
        return new User(id, firstName, lastName, user.getUsername(), user.getPasswordHash(), userRole);
    }



    public static List<User> getUsersList(){
        List<User> users = new ArrayList<>();

        try(Connection connection = DatabaseConnection.connectToDatabase()) {
            String sqlQuery = "SELECT * FROM USERS ";
            Statement stmt = connection.createStatement();
            stmt.execute(sqlQuery);
            ResultSet resultSet = stmt.getResultSet();

            mapResultSetToUsersList(resultSet, users);

        } catch (SQLException | IOException ex) {
            throw new RuntimeException(ex);
        }
        return users;
    }
    private static void mapResultSetToUsersList(ResultSet resultSet, List<User> users) throws SQLException {
        while (resultSet.next()){
            Long id = resultSet.getLong("ID");
            String  firstName = resultSet.getString("FIRST_NAME");
            String lastName = resultSet.getString("LAST_NAME");
            UserRole userRole = UserRole.valueOf(resultSet.getString("USER_ROLE"));
            String username = resultSet.getString("USERNAME");

            users.add(new User(id, firstName, lastName, userRole, username));
        }
    }


    public static Map<Long, User> getUsersMap(){
        Map<Long, User> users = new HashMap<>();

        try(Connection connection = DatabaseConnection.connectToDatabase()) {
            String sqlQuery = "SELECT * FROM USERS ";
            Statement stmt = connection.createStatement();
            stmt.execute(sqlQuery);
            ResultSet resultSet = stmt.getResultSet();

            mapResultSetToUsersMap(resultSet, users);

        } catch (SQLException | IOException ex) {
            throw new RuntimeException(ex);
        }

        return users;
    }

    private static void mapResultSetToUsersMap(ResultSet resultSet, Map<Long, User> users) throws SQLException {
        while (resultSet.next()){
            Long id = resultSet.getLong("ID");
            String  firstName = resultSet.getString("FIRST_NAME");
            String lastName = resultSet.getString("LAST_NAME");
            UserRole userRole = UserRole.valueOf(resultSet.getString("USER_ROLE"));
            String username = resultSet.getString("USERNAME");

            users.put(id, new User(id, firstName, lastName, userRole, username));
        }
    }


}
