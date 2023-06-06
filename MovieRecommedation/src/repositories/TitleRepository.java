package repositories;

import classes.cast.Actor;
import classes.cast.Director;
import database.DatabaseConfiguration;
import classes.title.Title;
import services.MainService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class TitleRepository {

    static MainService mainService = MainService.getInstance();
    public static void createTable() {
        String createTable = "CREATE TABLE IF NOT EXISTS TITLE " +
                "(id INT PRIMARY KEY, " +
                "title VARCHAR(200) NOT NULL, " +
                "language VARCHAR(100) NOT NULL);";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTable);
        } catch (SQLException e) {
            System.out.println("Error when creating TitleTable: " + e.getMessage());
        }
    }

    //TODO: addData

    public static void insertTitle(int id, String title, String language)
    {
        String Title = id + ",\"" + title + "\", \"" + language + "\"";

        String insertTitle = "INSERT INTO TITLE(id, title, language) VALUES(" + Title + ");";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            stmt.executeUpdate(insertTitle);
        }
        catch (SQLException e)
        {
            System.out.println("Error when inserting Title!");
        }
    }

    public void displayTitle()
    {
        String displayTitle= "SELECT * FROM TITLE;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            boolean empty = true;
            ResultSet resultSet = stmt.executeQuery(displayTitle);
            while (resultSet.next())
            {
                empty = false;
                System.out.println("id: " + resultSet.getInt(1));
                System.out.println("Title: " + resultSet.getString(2));
                System.out.println("Language: " + resultSet.getString(3));
                System.out.println();
            }

            if (empty)
            {
                System.out.println("No existing Titles!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error when displaying Titles!");
        }
    }

    public static void transformDBToLists(){

        String selectAll = "SELECT * FROM TITLE;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            boolean empty = true;
            ResultSet resultSet = stmt.executeQuery(selectAll);
            while (resultSet.next())
            {
                empty = false;
                Title title = mapToTitle(resultSet);
                mainService.getTitles().add(title);
            }

            if (empty)
            {
                System.out.println("No existing titles!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error when selecting titles!");
        }

    }

    public static Title getTitleById(int id)
    {
        String getTitleById = "SELECT * FROM TITLE WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(getTitleById))
        {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                return mapToTitle(resultSet);
            }
            return null;

        }
        catch (SQLException e)
        {
            System.out.println("Error when getting title by id!");
            return null;
        }

    }

    public void updateTitle (String title, String language, int id)
    {
        String updateTitle = "UPDATE TITLE SET title=?, language=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateTitle))
        {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, language);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error when updating Title!");
        }
    }

    public static void deleteTitleById(int id)
    {
        String deleteTitleById = "DELETE FROM TITLE WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteTitleById))
        {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error when deleting Title!");
        }
    }

    public static Title mapToTitle(ResultSet resultSet) throws SQLException
    {
        List<String> genre = GenreRepository.getGenreForTitle(resultSet.getInt(1));
        return new Title(resultSet.getInt(1), resultSet.getString(2), genre, resultSet.getString(3));


    }

}
