package repositories;

import classes.cast.Director;
import classes.title.*;
import classes.user.User;
import database.DatabaseConfiguration;
import services.MainService;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReviewRepository {

    static MainService mainService = MainService.getInstance();

    //TODO: addData

    public static void insertReview(int id, String username, int title_id, int rating, String comment, LocalDateTime dateTime) {
        String insertReview = "INSERT INTO REVIEW(id, username, title_id, rating, comment, datetime) VALUES(?, ?, ?, ?, ?, ?);";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement stmt = connection.prepareStatement(insertReview)) {
            stmt.setInt(1, id);
            stmt.setString(2, username);
            stmt.setInt(3, title_id);
            stmt.setInt(4, rating);
            stmt.setString(5, comment);
            stmt.setTimestamp(6, Timestamp.valueOf(dateTime));

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error when inserting Review!" + e);
        }
    }

    public static int getMaxReviewId(){
        String getMaxReviewId= "SELECT MAX(id) FROM REVIEW;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {

            ResultSet resultSet = stmt.executeQuery(getMaxReviewId);
            if(resultSet.next())
            {
                return resultSet.getInt(1);
            }
            return 0;

        }
        catch (SQLException e)
        {
            System.out.println("Error when selecting max id from Reviews!");
        }
        return 0;
    }

    public void displayReview()
    {
        String displayReview= "SELECT * FROM REVIEW;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            boolean empty = true;
            ResultSet resultSet = stmt.executeQuery(displayReview);
            while (resultSet.next())
            {
                empty = false;
                System.out.println("id: " + resultSet.getInt(1));
                System.out.println("User: " + resultSet.getString(2));
                System.out.println("Title: " + resultSet.getInt(3));
                System.out.println("Rating: " + resultSet.getInt(4));
                System.out.println("Review: " + resultSet.getString(5));
                System.out.println("DateTime: " + resultSet.getDate(6));//TODO: nu cred ca e bine
                System.out.println();
            }

            if (empty)
            {
                System.out.println("No existing Reviews!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error when displaying Reviews!");
        }
    }

    public static List<Review> getReviewsByUsername(String username){

        List<Review> reviews = new ArrayList<>();

        String selectByUsername = "SELECT * FROM REVIEW WHERE username=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectByUsername))
        {
            preparedStatement.setString(1, username);
            boolean empty = true;
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                empty = false;
                Review review = mapToReview(resultSet);
                reviews.add(review);
            }

        }
        catch (SQLException e)
        {
            System.out.println("Error when selecting Reviews for user!");
        }

        return reviews;

    }

    public static List<Review> getReviewsByTitle(int title_id){

        List<Review> reviews = new ArrayList<>();
        String selectByTitle = "SELECT * FROM REVIEW WHERE title_id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectByTitle))
        {
            preparedStatement.setInt(1, title_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                Review review = mapToReview(resultSet);
                reviews.add(review);
            }


        }
        catch (SQLException e)
        {
            System.out.println("Error when selecting Reviews for title!");
        }

        return reviews;
    }


    public Review getReviewByTitleAndUsername(int title_id, String username)
    {
        String getReviewByTitleAndUsername = "SELECT * FROM REVIEW WHERE title_id=? AND username=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(getReviewByTitleAndUsername))
        {
            preparedStatement.setInt(1, title_id);
            preparedStatement.setString(2, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToReview(resultSet);
        }
        catch (SQLException e)
        {
            System.out.println("Error when getting review by title and username!");
            return null;
        }

    }

    public void updateReview (String username, int title_id, int rating, String comment, int id)
    {
        String updateReview = "UPDATE REVIEW SET username=?, title_id=?, rating=?, comment=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateReview))
        {
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, title_id);
            preparedStatement.setInt(3, rating);
            preparedStatement.setString(4, comment);
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error when updating Review!");
        }
    }

    public void deleteReviewByTitleAndUsername(int title_id, String username)
    {
        String deleteReviewByTitleAndUsername = "DELETE FROM REVIEW WHERE title_id=? AND username=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteReviewByTitleAndUsername))
        {
            preparedStatement.setInt(1, title_id);
            preparedStatement.setString(2, username);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error when deleting Review!");
        }
    }

    private static Review mapToReview(ResultSet resultSet) throws SQLException {
        User user = null;
        String usern = resultSet.getString(2);

        for (User u : mainService.getUsers()) {
            if (Objects.equals(u.getUsername(), usern)) {
                user = u;
                break;
            }
        }

        Title title = null;
        int titlId = resultSet.getInt(3);

        for (Title t : mainService.getTitles()) {
            if (t.getId() == titlId) {
                title = t;
                break;
            }
        }

        Timestamp dateTime = resultSet.getTimestamp(6);
        LocalDateTime localDateTime = dateTime.toLocalDateTime();


        return new Review(resultSet.getInt(1), user, title, resultSet.getInt("rating"), resultSet.getString(5), localDateTime);

    }
}
