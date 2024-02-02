package hr.production.slovic_projektni.utils;

import hr.production.slovic_projektni.constants.Constants;
import hr.production.slovic_projektni.model.DateAndTime;
import hr.production.slovic_projektni.model.Project;
import hr.production.slovic_projektni.model.Subject;
import hr.production.slovic_projektni.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseUtilProject {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtilProject.class);

    public static void deleteUserProjects(Long userId) {
        try (Connection connection = DatabaseConnection.connectToDatabase()) {
            String deleteProjectSql = "DELETE FROM PROJECT WHERE AUTHOR_ID = ?";
            PreparedStatement pstmt = connection.prepareStatement(deleteProjectSql);
            pstmt.setLong(1, userId);
            pstmt.execute();
        } catch (SQLException | IOException ex) {
            String message = "Problem with deleting Project from Database.";
            Constants.errorAlert("Problem with deleting", message);
            logger.error(message);
        }
    }

    public static void deleteProject(Long projectId) {
        try (Connection connection = DatabaseConnection.connectToDatabase()) {
            String deleteProjectSql = "DELETE FROM PROJECT WHERE ID = ?";
            PreparedStatement pstmt = connection.prepareStatement(deleteProjectSql);
            pstmt.setLong(1, projectId);
            pstmt.execute();
        } catch (SQLException | IOException ex) {
            String message = "Problem with deleting Project from Database.";
            Constants.errorAlert("Problem with deleting", message);
            logger.error(message);
        }
    }


    public static void updateProject(Project project) {
        try (Connection connection = DatabaseConnection.connectToDatabase()) {

            String updateProjectSql = "UPDATE PROJECT " +
                    "SET NAME = ?, " +
                    "DESCRIPTION = ?, " +
                    "SUBJECT = ? " +
                    "WHERE ID = ?";

            PreparedStatement pstmt = connection.prepareStatement(updateProjectSql);
            pstmt.setString(1, project.getName());
            pstmt.setString(2, project.getDescription());
            pstmt.setString(3, project.getSubject().toString());
            pstmt.setLong(4, project.getId());
            pstmt.executeUpdate();

        } catch (SQLException | IOException ex) {

            String message = "Problem with uploading Projects data.";
            Constants.errorAlert("Edit project data", message);
            logger.error(message);
        }
    }

    public static void saveProject(Project newProject) {
        try (Connection connection = DatabaseConnection.connectToDatabase()) {

            String insertProjectSql = "INSERT INTO PROJECT (NAME, DESCRIPTION, AUTHOR_ID, SUBJECT, START_DATE)\n" +
                    "VALUES (?, ?, ?, ?, ?);\n";

            PreparedStatement pstmt = connection.prepareStatement(insertProjectSql);
            pstmt.setString(1, newProject.getName());
            pstmt.setString(2, newProject.getDescription());
            pstmt.setLong(3, newProject.getAuthor().getId());
            pstmt.setString(4, newProject.getSubject().toString());
            pstmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.execute();

        } catch (SQLException | IOException ex) {

            String message = "Problem with saving new project.";
            Constants.errorAlert("Problem with saving", message);
            logger.error(message);
        }
    }


    public static List<Project> getProjects() {
        Map<Long, User> users = DatabaseUtilUsers.getUsersMap();
        List<Project> projects = new ArrayList<>();

        try (Connection connection = DatabaseConnection.connectToDatabase()) {
            String sqlQuery = "SELECT PROJECT.*, \n" +
                    "FROM PROJECT\n" +
                    "JOIN USERS ON PROJECT.AUTHOR_ID = USERS.ID\n" +
                    "WHERE PROJECT.AUTHOR_ID = ? ";

            for (User user : users.values()) {
                PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
                pstmt.setLong(1, user.getId());
                ResultSet resultSet = pstmt.executeQuery();

                mapResultSetToProjectList(user, resultSet, projects, users);
            }
        } catch (SQLException | IOException ex) {
            String message = "Problem with getting projects from Database.";
            Constants.errorAlert("Getting projects", message);
            logger.error(message);
        }

        return projects;
    }

    private static void mapResultSetToProjectList(User user, ResultSet resultSet, List<Project> projects, Map<Long, User> users) throws SQLException {
        while (resultSet.next()) {
            Long id = resultSet.getLong("ID");
            String name = resultSet.getString("NAME");
            String description = resultSet.getString("DESCRIPTION");
            Subject subject = Subject.valueOf(resultSet.getString("SUBJECT"));
            LocalDateTime postDate = resultSet.getTimestamp("START_DATE").toLocalDateTime();

            projects.add(new Project(id, name, description, new DateAndTime(postDate), user, subject, DatabaseUtilComment.getComments(id, users)));
        }
    }
}
