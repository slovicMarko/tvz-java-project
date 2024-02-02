package hr.production.slovic_projektni.utils;

import hr.production.slovic_projektni.constants.Constants;
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

    public static void deleteUser(Long userId){
        try (Connection connection = DatabaseConnection.connectToDatabase()) {
            String deleteUserSql = "DELETE FROM USERS WHERE ID = ?";
            PreparedStatement pstmt = connection.prepareStatement(deleteUserSql);
            pstmt.setLong(1, userId);
            pstmt.execute();
        } catch (SQLException | IOException ex) {
            String message = "Problem with deleting user from Database.";
            Constants.errorAlert("Problem with deleting", message);
            logger.error(message);
        }
    }


    public static void changeUserPassword(Long id, String newPassword) {
        try (Connection connection = DatabaseConnection.connectToDatabase()) {
            String updatePasswordSql = "UPDATE USERS " +
                    "SET PASSWORD_HASHED = ? " +
                    "WHERE ID = ?";

            PreparedStatement pstmt = connection.prepareStatement(updatePasswordSql);
            pstmt.setString(1, User.hashPassword(newPassword));
            pstmt.setLong(2, id);
            pstmt.executeUpdate();

            Constants.infoAlert("Password changed!", "New password is successfully stored.");

        } catch (SQLException | IOException e) {
            String message = "Problem with changing User password.";
            Constants.errorAlert("Password not changed!", message);
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
            Constants.infoAlert("User roles changed!","Users roles have been successfully stored.");

        } catch (SQLException | IOException e) {
            String message = "New user roles are not stored.";
            Constants.errorAlert("Problem with saving changes", message);
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
            Constants.errorAlert("Problem with saving", message);
            logger.error(message);
        }
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
            pstmt.setString(1, user.getUsername().username());
            pstmt.executeQuery();
            ResultSet resultSet = pstmt.getResultSet();

            resultSet.next();
            id = resultSet.getLong("ID");
            userRole = UserRole.valueOf(resultSet.getString("USER_ROLE").toUpperCase());
            firstName = resultSet.getString("FIRST_NAME");
            lastName = resultSet.getString("LAST_NAME");

        } catch (SQLException | IOException ex) {
            String message = "Problem with getting active user.";
            Constants.errorAlert("Active user", message);
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
            String message = "Cannot get data for users.";
            Constants.errorAlert("Getting users", message);
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
            String message = "Cannot get data for users.";
            Constants.errorAlert("Getting users", message);
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
