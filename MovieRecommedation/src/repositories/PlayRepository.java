package repositories;

import classes.cast.Actor;
import classes.cast.Director;
import classes.title.Movie;
import classes.title.Series;
import classes.title.Title;
import classes.user.User;
import database.DatabaseConfiguration;
import services.MainService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayRepository {
    static MainService mainService = MainService.getInstance();

    //TODO: addData

    public static void insertPlay(int cast_id, int title_id)
    {
        String Play = cast_id + ", " + title_id;

        String insertPlay = "INSERT INTO PLAY(cast_id, title_id) VALUES(" + Play + ");";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            stmt.executeUpdate(insertPlay);
        }
        catch (SQLException e)
        {
            System.out.println("Error when inserting in Play!");
        }
    }

    public void displayPlay()
    {
        String displayPlay= "SELECT * FROM PLAY;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            boolean empty = true;
            ResultSet resultSet = stmt.executeQuery(displayPlay);
            while (resultSet.next())
            {
                empty = false;
                System.out.println("cast_id: " + resultSet.getInt(1));
                System.out.println("Title id: " + resultSet.getInt(2));
                System.out.println();
            }

            if (empty)
            {
                System.out.println("No existing Play!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error when displaying Play!");
        }
    }

    public static List<Actor> getPlayByTitle(int title_id){

        List<Actor> actors = new ArrayList<>();

        String selectByTitle = "SELECT * FROM PLAY WHERE title_id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectByTitle))
        {
            preparedStatement.setInt(1, title_id);
            boolean empty = true;
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                empty = false;
                Actor actor = ActorRepository.getActorById(resultSet.getInt(1));
                actors.add(actor);
            }

            if (empty)
            {
                System.out.println("No existing Play!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error when selecting Play!");
        }
        return actors;
    }


    //TODO: getPlayBY
    public static void deletePlayByCast_idAndTitle(int cast_id, int title_id)
    {
        String deletePlayByCast_idAndTitle = "DELETE FROM PLAY WHERE cast_id=? AND title_id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deletePlayByCast_idAndTitle))
        {
            preparedStatement.setInt(1, cast_id);
            preparedStatement.setInt(2, title_id);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error when deleting Play!");
        }
    }

}