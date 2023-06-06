package repositories;

import classes.title.Movie;
import classes.title.TitleCollection;
import database.DatabaseConfiguration;
import classes.title.TitleCollection;
import services.MainService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TitleCollectionRepository {

    static MainService mainService = MainService.getInstance();
    public static void createTable(){
        String createTable = "CREATE TABLE IF NOT EXISTS COLLECTION " +
                "(id INT PRIMARY KEY, " +
                "numberTitles INT NOT NULL);";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            stmt.execute(createTable);
        }
        catch (SQLException e)
        {
            System.out.println("Error when creating CollectionTable!");
        }
    }

    //TODO: addData

    public static void insertTitleCollection(int id, int numberTitles)
    {
        String TitleCollection = id + ", " + numberTitles;

        String insertTitleCollection = "INSERT INTO COLLECTION(id, numberTitles) VALUES(" + TitleCollection + ");";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            stmt.executeUpdate(insertTitleCollection);
        }
        catch (SQLException e)
        {
            System.out.println("Error when inserting TitleCollection!");
        }
    }

    public void displayTitleCollection()
    {
        String displayTitleCollection= "SELECT * FROM COLLECTION;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            boolean empty = true;
            ResultSet resultSet = stmt.executeQuery(displayTitleCollection);
            while (resultSet.next())
            {
                empty = false;
                System.out.println("id: " + resultSet.getInt(1));
                System.out.println("Number of titles in collection: " + resultSet.getInt(2));
                System.out.println();
            }

            if (empty)
            {
                System.out.println("No existing TitleCollections!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error when displaying TitleCollections!");
        }
    }

    public static void transformDBToLists(){

        String selectAll = "SELECT * FROM COLLECTION;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            boolean empty = true;
            ResultSet resultSet = stmt.executeQuery(selectAll);
            while (resultSet.next())
            {
                empty = false;
                TitleCollection collection = getTitleCollectionById(resultSet.getInt(1));
                if(collection!=null){
                    mainService.getCollections().add(collection);
                }
            }

            if (empty)
            {
                System.out.println("No existing TitleCollections!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error when selecting TitleCollections!");
        }

    }

    public static TitleCollection getTitleCollectionById(int id)
    {
        String getTitleCollectionById = "SELECT t.id, t.title, t.language, c.numberTitles FROM title t, collection c WHERE t.id=c.id AND c.id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(getTitleCollectionById))
        {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return mapToTitleCollection(resultSet);
            }
            return null;

        }
        catch (SQLException e)
        {
            System.out.println("Error when getting collection by id!");
            return null;
        }

    }

    public void updateTitleCollection (String title, String language, int numberTitles, int id)
    {
        String updateTitle = "UPDATE TITLE SET title=?, language=? WHERE id=?";
        String updateTitleCollection = "UPDATE COLLECTION SET numberTitles=? WHERE id=?";

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

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateTitleCollection))
        {
            preparedStatement.setInt(1, numberTitles);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error when updating TitleCollection!");
        }
    }

    public static void deleteTitleCollectionById(int id)
    {
        String deleteTitleCollectionById = "DELETE FROM COLLECTION WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteTitleCollectionById))
        {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error when deleting TitleCollection!");
        }


        String deleteTitleById = "DELETE FROM TITLE WHERE id=?";
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

    private static TitleCollection mapToTitleCollection(ResultSet resultSet) throws SQLException
    {
        try {

            List<String> g = GenreRepository.getGenreForTitle(resultSet.getInt(1));
            List<Movie> m = MovieRepository.getMoviesByCollection(resultSet.getInt(1));


            return new TitleCollection(resultSet.getInt(1), resultSet.getString(2), g,
                    resultSet.getString(3), resultSet.getInt(4), m);
        }catch(Exception e){
            System.out.println("Exceptie la mapToTitleCollection" + e + "!!!!");
        }
        return null;
    }

}