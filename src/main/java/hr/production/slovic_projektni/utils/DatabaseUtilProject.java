package hr.production.slovic_projektni.utils;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.model.Project;
import hr.production.slovic_projektni.model.Subject;
import hr.production.slovic_projektni.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseUtilProject {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtilProject.class);

    public static void deleteProject(Long projectId) {
        try (Connection connection = DatabaseConnection.connectToDatabase()) {
            String deleteProjectSql = "DELETE FROM PROJECT WHERE ID = ?";
            PreparedStatement pstmt = connection.prepareStatement(deleteProjectSql);
            pstmt.setLong(1, projectId);
            pstmt.executeUpdate();
        } catch (SQLException | IOException ex) {

            String message = "Problem while deleting Project from Database.";
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
            logger.error(message);
        }
    }

    public static void saveProject(String projectName, String projectDescription, Subject subject) {
        try (Connection connection = DatabaseConnection.connectToDatabase()) {

            String insertProjectSql = "INSERT INTO PROJECT (NAME, DESCRIPTION, AUTHOR_ID, SUBJECT, START_DATE)\n" +
                    "VALUES (?, ?, ?, ?, ?);\n";

            PreparedStatement pstmt = connection.prepareStatement(insertProjectSql);
            pstmt.setString(1, projectName);
            pstmt.setString(2, projectDescription);
            pstmt.setLong(3, MainApplication.getActiveUser().getId());
            pstmt.setString(4, subject.toString());
            pstmt.setDate(5, Date.valueOf(LocalDate.now()));
            pstmt.execute();

        } catch (SQLException | IOException ex) {

            String message = "Problem with saving new project.";
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
            String message = "Problem with catching projects from Database.";
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
            LocalDate startDate = resultSet.getDate("START_DATE").toLocalDate();

            projects.add(new Project(id, name, description, startDate, user, subject, DatabaseUtilComment.getComments(id, users)));
        }
    }
}
