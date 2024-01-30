package hr.production.slovic_projektni.utils;

import hr.production.slovic_projektni.controllers.NavigationMethods;
import hr.production.slovic_projektni.model.User;
import hr.production.slovic_projektni.model.UserRole;
import hr.production.slovic_projektni.model.Username;
import javafx.scene.control.Alert;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.sql.*;
import java.util.*;

public class DatabaseUtilUsers {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtilUsers.class);

    public static void changeUserPassword(Long id, String newPassword) {
        try (Connection connection = DatabaseConnection.connectToDatabase()) {
            String updatePasswordSql = "UPDATE USERS " +
                    "SET PASSWORD_HASHED = ? " +
                    "WHERE ID = ?";

            PreparedStatement pstmt = connection.prepareStatement(updatePasswordSql);
            pstmt.setString(1, User.hashPassword(newPassword));
            pstmt.setLong(2, id);
            pstmt.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Password changed!");
            alert.setContentText("New password is successful stored.");
            alert.showAndWait();

        } catch (SQLException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Password not changed!");
            alert.setContentText("Problem with saving new Password");
            alert.showAndWait();

            String message = "Problem with changing User password.";
            logger.error(message);
        }
    }


    public static void saveManageUsersSettings(List<User> users) {
        try (Connection connection = DatabaseConnection.connectToDatabase()) {
            String updateUserSql = "UPDATE USERS " +
                    "SET USER_ROLE = ? " +
                    "WHERE ID = ?";

            for (User user : users) {
                PreparedStatement pstmt = connection.prepareStatement(updateUserSql);
                pstmt.setString(1, user.getUserRole().toString().toUpperCase());
                pstmt.setLong(2, user.getId());
                pstmt.executeUpdate();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("User roles changed!");
            alert.setContentText("Manage users changes successfully stored.");
            alert.showAndWait();


        } catch (SQLException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Problem with saving changes");
            alert.setContentText("Manage users changes aren't stored.");
            alert.showAndWait();

            String message = "Manage users changes aren't changed.";
            logger.error(message);
        }
    }


    public static void saveUser(User newUser) {
        try (Connection connection = DatabaseConnection.connectToDatabase()) {
            String insertUserSql = "INSERT INTO USERS (FIRST_NAME, LAST_NAME, USER_ROLE, USERNAME, PASSWORD_HASHED ) " +
                    "VALUES(?, ?, ?, ?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(insertUserSql);
            pstmt.setString(1, newUser.getFirstName());
            pstmt.setString(2, newUser.getLastName());
            pstmt.setString(3, newUser.getUserRole().getName().toUpperCase());
            pstmt.setString(4, newUser.getUsername().username());
            pstmt.setString(5, newUser.getPasswordHash());
            pstmt.execute();

        } catch (SQLException | IOException ex) {
            String message = "Problem with saving new user.";
            logger.error(message);
        }
    }


/*
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
            String message = "Problem with catching next ID.";
            logger.error(message);
        }
        return maxId+1;
    }

 */

    public static User activeUser(User user) {
        Long id;
        String firstName, lastName;
        UserRole userRole;
        try (Connection connection = DatabaseConnection.connectToDatabase()) {
            String sqlQuery = "SELECT ID, FIRST_NAME, LAST_NAME, USER_ROLE\n" +
                    "FROM USERS  \n" +
                    "WHERE USERNAME = ?;";

            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setString(1, user.getUsername().username());
            pstmt.executeQuery();
            ResultSet resultSet = pstmt.getResultSet();

            resultSet.next();
            id = resultSet.getLong("ID");
            userRole = UserRole.valueOf(resultSet.getString("USER_ROLE").toUpperCase());
            firstName = resultSet.getString("FIRST_NAME");
            lastName = resultSet.getString("LAST_NAME");

        } catch (SQLException | IOException ex) {
            String message = "Problem with catching active user.";
            logger.error(message);
            throw new RuntimeException(ex);
        }
        return new User(id, firstName, lastName, user.getUsername(), user.getPasswordHash(), userRole);
    }


    public static List<User> getUsersList() {
        List<User> users = new ArrayList<>();

        try (Connection connection = DatabaseConnection.connectToDatabase()) {
            String sqlQuery = "SELECT * FROM USERS ";
            Statement stmt = connection.createStatement();
            stmt.execute(sqlQuery);
            ResultSet resultSet = stmt.getResultSet();

            mapResultSetToUsersList(resultSet, users);

        } catch (SQLException | IOException ex) {
            String message = "Cannot fetch data for Users .";
            logger.error(message);
        }
        return users;
    }

    private static void mapResultSetToUsersList(ResultSet resultSet, List<User> users) throws SQLException {
        while (resultSet.next()) {
            Long id = resultSet.getLong("ID");
            String firstName = resultSet.getString("FIRST_NAME");
            String lastName = resultSet.getString("LAST_NAME");
            UserRole userRole = UserRole.valueOf(resultSet.getString("USER_ROLE"));
            String username = resultSet.getString("USERNAME");
            String password = resultSet.getString("PASSWORD_HASHED");


            users.add(new User(id, firstName, lastName, new Username(username), password, userRole));
        }
    }


    public static Map<Long, User> getUsersMap() {
        Map<Long, User> users = new HashMap<>();

        try (Connection connection = DatabaseConnection.connectToDatabase()) {
            String sqlQuery = "SELECT * FROM USERS ";
            Statement stmt = connection.createStatement();
            stmt.execute(sqlQuery);
            ResultSet resultSet = stmt.getResultSet();

            mapResultSetToUsersMap(resultSet, users);

        } catch (SQLException | IOException ex) {
            String message = "Cannot fetch data for Users .";
            logger.error(message);
        }

        return users;
    }

    private static void mapResultSetToUsersMap(ResultSet resultSet, Map<Long, User> users) throws SQLException {
        while (resultSet.next()) {
            Long id = resultSet.getLong("ID");
            String firstName = resultSet.getString("FIRST_NAME");
            String lastName = resultSet.getString("LAST_NAME");
            UserRole userRole = UserRole.valueOf(resultSet.getString("USER_ROLE"));
            String username = resultSet.getString("USERNAME");
            String password = resultSet.getString("PASSWORD_HASHED");

            users.put(id, new User(id, firstName, lastName, new Username(username), password, userRole));
        }
    }

}
