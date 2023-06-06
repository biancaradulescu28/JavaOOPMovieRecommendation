package repositories;


import classes.title.Title;
import classes.user.User;
import database.DatabaseConfiguration;
import services.MainService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryRepository {

    static MainService mainService = MainService.getInstance();
    //TODO: addData

    public static void insertHistory(String username, int title_id)
    {
        String History = "\"" + username + "\", " + title_id;

        String insertHistory = "INSERT INTO HISTORY(username, title_id) VALUES(" + History + ");";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            stmt.executeUpdate(insertHistory);
        }
        catch (SQLException e)
        {
            System.out.println("Error when inserting in History!");
        }
    }

    public void displayHistory()
    {
        String displayHistory= "SELECT * FROM HISTORY;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            boolean empty = true;
            ResultSet resultSet = stmt.executeQuery(displayHistory);
            while (resultSet.next())
            {
                empty = false;
                System.out.println("Username: " + resultSet.getString(1));
                System.out.println("Title id: " + resultSet.getInt(2));
                System.out.println();
            }

            if (empty)
            {
                System.out.println("No existing History!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error when displaying History!");
        }
    }


    public static List<Title> getHistoryByUsername(String username){

        List<Title> titles = new ArrayList<>();
        String selectAll = "SELECT * FROM HISTORY WHERE username=?";

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
            System.out.println("Error when selecting History!");
        }
        return titles;
    }
    public void deleteHistoryByUsernameAndTitle(String username, int title_id)
    {
        String deleteHistoryByUsernameAndTitle = "DELETE FROM HISTORY WHERE username=? AND title_id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteHistoryByUsernameAndTitle))
        {
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, title_id);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error when deleting History!");
        }
    }
    public static void deleteHistoryByTitle(int title_id)
    {
        String deleteHistoryByUsernameAndTitle = "DELETE FROM HISTORY WHERE title_id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteHistoryByUsernameAndTitle))
        {
            preparedStatement.setInt(1, title_id);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error when deleting History by title!");
        }
    }

}
