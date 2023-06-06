package repositories;

import classes.cast.Actor;
import classes.title.Movie;
import classes.title.Series;
import classes.title.Title;
import database.DatabaseConfiguration;
import services.MainService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreRepository {

    static MainService mainService = MainService.getInstance();
    //TODO: addData

    public static void insertGenre(String genre, int title_id)
    {
        String Genre = "\"" + genre + "\", " + title_id;

        String insertGenre = "INSERT INTO GENRE(genre, title_id) VALUES(" + Genre + ");";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            stmt.executeUpdate(insertGenre);
        }
        catch (SQLException e)
        {
            System.out.println("Error when inserting in Genre!");
        }
    }

    public void displayGenre()
    {
        String displayGenre= "SELECT * FROM GENRE;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            boolean empty = true;
            ResultSet resultSet = stmt.executeQuery(displayGenre);
            while (resultSet.next())
            {
                empty = false;
                System.out.println("Genre: " + resultSet.getString(1));
                System.out.println("Title id: " + resultSet.getInt(2));
                System.out.println();
            }

            if (empty)
            {
                System.out.println("No existing Genre!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error when displaying Genre!");
        }
    }


    public static List<String> getGenreForTitle(int title_id){

        List<String> genre = new ArrayList<>();

        String selectByTitle = "SELECT * FROM GENRE WHERE title_id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectByTitle))
        {
            preparedStatement.setInt(1, title_id);
            boolean empty = true;
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                empty = false;
                genre.add(resultSet.getString(1));
            }

            if (empty)
            {
                System.out.println("No existing Genre!");
            }

        }
        catch (SQLException e)
        {
            System.out.println("Error when selecting Genre!");
        }
        return genre;

    }


    public static void deleteGenreByGenreAndTitle(String genre, int title_id)
    {
        String deleteGenreByGenreAndTitle = "DELETE FROM GENRE WHERE genre=? AND title_id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteGenreByGenreAndTitle))
        {
            preparedStatement.setString(1, genre);
            preparedStatement.setInt(2, title_id);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error when deleting Genre!");
        }
    }

}