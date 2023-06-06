package repositories;

import classes.cast.Actor;
import classes.cast.Director;
import classes.title.Title;
import classes.user.User;
import repositories.TitleRepository;
import database.DatabaseConfiguration;
import services.MainService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoritesRepository {

    static MainService mainService = MainService.getInstance();

    //TODO: addData

    public static void insertFavorites(String username, int title_id)
    {
        String Favorites = "\"" + username + "\", " + title_id;

        String insertFavorites = "INSERT INTO FAVORITES(username, title_id) VALUES(" + Favorites + ");";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            stmt.executeUpdate(insertFavorites);
        }
        catch (SQLException e)
        {
            System.out.println("Error when inserting in Favorites!");
        }
    }

    public void displayFavorites()
    {
        String displayFavorites= "SELECT * FROM FAVORITES;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            boolean empty = true;
            ResultSet resultSet = stmt.executeQuery(displayFavorites);
            while (resultSet.next())
            {
                empty = false;
                System.out.println("Username: " + resultSet.getString(1));
                System.out.println("Title id: " + resultSet.getInt(2));
                System.out.println();
            }

            if (empty)
            {
                System.out.println("No existing Favorites!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error when displaying Favorites!");
        }
    }

    public static List<Title> getFavoritesByUsername(String username){

        List<Title>titles = new ArrayList<>();
        String selectAll = "SELECT * FROM FAVORITES WHERE username=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectAll))
        {
            preparedStatement.setString(1, username);
            boolean empty = true;
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                empty = false;
                int title_id = resultSet.getInt(2);
                Title title = TitleRepository.getTitleById(title_id);
                titles.add(title);
            }

        }
        catch (SQLException e)
        {
            System.out.println("Error when selecting Favorites!");
        }
        return titles;
    }


    //TODO: getFavoritesBY
    public static void deleteFavoritesByUsernameAndTitle(String username, int title_id)
    {
        String deleteFavoritesByUsernameAndTitle = "DELETE FROM FAVORITES WHERE username=? AND title_id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteFavoritesByUsernameAndTitle))
        {
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, title_id);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error when deleting Favorites!");
        }
    }

    public static void deleteFavoritesByTitle(int title_id)
    {
        String deleteFavoritesByUsernameAndTitle = "DELETE FROM FAVORITES WHERE title_id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteFavoritesByUsernameAndTitle))
        {
            preparedStatement.setInt(1, title_id);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error when deleting Favorites by title!");
        }
    }

}

