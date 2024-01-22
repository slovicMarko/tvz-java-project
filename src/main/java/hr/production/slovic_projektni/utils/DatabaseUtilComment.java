package hr.production.slovic_projektni.utils;

import hr.production.slovic_projektni.model.Comment;
import hr.production.slovic_projektni.model.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseUtilComment {


    public static List<Comment> getComments(Long projectId, Map<Long, User> users){
        List<Comment> comments = new ArrayList<>();

        try(Connection connection = DatabaseConnection.connectToDatabase()){
            String sqlQuery = "SELECT COMMENT.*\n" +
                    "FROM COMMENT\n" +
                    "JOIN PROJECT ON COMMENT.PROJECT_ID = PROJECT.ID\n" +
                    "WHERE COMMENT.PROJECT_ID  = ? ";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setLong(1, projectId);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()){
                Long id = resultSet.getLong("ID");
                User author = users.get(resultSet.getLong("AUTHOR_ID"));
                String content = resultSet.getString("CONTENT");
                Integer likes = resultSet.getInt("LIKES");

                comments.add(new Comment(author, content, likes));
            }

        } catch (SQLException | IOException ex){
            ex.printStackTrace();
        }

        return comments;
    }
}
