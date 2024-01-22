package hr.production.slovic_projektni.utils;

import hr.production.slovic_projektni.model.Project;
import hr.production.slovic_projektni.model.Subject;
import hr.production.slovic_projektni.model.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseUtilProject {


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
            ex.printStackTrace();
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

            projects.add(new Project(id, name, description,startDate, user, subject, DatabaseUtilComment.getComments(id, users)));
        }
    }
}
