package repositories;

import classes.cast.Actor;
import classes.cast.Director;
import classes.title.Movie;
import classes.title.Review;
import classes.title.TitleCollection;
import database.DatabaseConfiguration;
import classes.title.Series;
import services.MainService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeriesRepository {

    static MainService mainService = MainService.getInstance();
    public static void createTable() {
        String createTable = "CREATE TABLE IF NOT EXISTS SERIES " +
                "(id INT PRIMARY KEY, " +
                "releaseYear INT NOT NULL, " +
                "finishYear INT, " +
                "nrSeasons INT NOT NULL, " +
                "collection_id INT," +
                "director_id INT," +
                "FOREIGN KEY (collection_id) REFERENCES collection(id)," +
                "FOREIGN KEY (director_id) REFERENCES director(cast_id));";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTable);
        } catch (SQLException e) {
            System.out.println("Error when creating SeriesTable: " + e.getMessage());
        }
    }

    //TODO: addData

    public static void insertSeries(int id, int releaseYear, int finishYear, int nrSeasons, Integer collection_id, int director_id)
    {
        String Series = id + ", " + releaseYear + ", " + finishYear + ", " + nrSeasons + ", " + collection_id + ", " + director_id;

        String insertSeries = "INSERT INTO SERIES(id, releaseYear, finishYear, nrSeasons, collection_id, director_id) VALUES(" + Series + ");";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            stmt.executeUpdate(insertSeries);
        }
        catch (SQLException e)
        {
            System.out.println("Error when inserting Series!");
        }
    }

    public void displaySeries()
    {
        String displaySeries= "SELECT * FROM SERIES;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            boolean empty = true;
            ResultSet resultSet = stmt.executeQuery(displaySeries);
            while (resultSet.next())
            {
                empty = false;
                System.out.println("id: " + resultSet.getInt(1));
                System.out.println("Release year: " + resultSet.getInt(2));
                System.out.println("End year: " + resultSet.getInt(3));
                System.out.println("Number seasons: " + resultSet.getInt(4));
                System.out.println("CollectionId: " + resultSet.getInt(5));
                System.out.println("DirectorId: " + resultSet.getInt(6));
                System.out.println();
            }

            if (empty)
            {
                System.out.println("No existing Series!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error when displaying Series!");
        }
    }

    public static void transformDBToLists(){

        String selectAll = "SELECT * FROM SERIES;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            boolean empty = true;
            ResultSet resultSet = stmt.executeQuery(selectAll);
            while (resultSet.next())
            {
                empty = false;
                Series series = getSeriesById(resultSet.getInt(1));
                mainService.getSeriesL().add(series);
            }

            if (empty)
            {
                System.out.println("No existing series!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error when selecting series!");
        }

    }

    public static Series getSeriesById(int id)
    {
        String getSeriesById = "SELECT t.id, t.title, t.language, s.releaseYear, s.finishYear, s.nrSeasons, s.collection_id, s.director_id FROM title t, series s WHERE t.id=s.id AND s.id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(getSeriesById))
        {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return mapToSeries(resultSet);
            }
            return null;

        }
        catch (SQLException e)
        {
            System.out.println("Error when getting series by id!");
            return null;
        }

    }

    public void updateSeries (String title, String language, int director_id, int releaseYear, int finishYear, int nrSeasons, int collection_id, int id)
    {
        String updateTitle = "UPDATE TITLE SET title=?, language=? WHERE id=?";
        String updateSeries = "UPDATE SERIES SET releaseYear=?, finishYear=?, nrSeasons=?, collection_id=?, director_id=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateTitle))
        {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, language);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error when updating Title!");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSeries))
        {
            preparedStatement.setInt(1, releaseYear);
            preparedStatement.setInt(2, finishYear);
            preparedStatement.setInt(3, nrSeasons);
            preparedStatement.setInt(4, collection_id);
            preparedStatement.setInt(5, director_id);
            preparedStatement.setInt(6, id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error when updating Series!");
        }
    }

    public static void deleteSeriesById(int id)
    {
        String deleteSeriesById = "DELETE FROM SERIES WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSeriesById))
        {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error when deleting Series!");
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

    private static Series mapToSeries(ResultSet resultSet) throws SQLException
    {
        Director dir = new Director();
        int dirId = resultSet.getInt(8);

        for(Director d: mainService.getDirectors()){
            if(d.getCastId() == dirId){
                dir = d;
                break;
            }
        }

        List<String> g = GenreRepository.getGenreForTitle(resultSet.getInt(1));
        List<Actor>a = PlayRepository.getPlayByTitle(resultSet.getInt(1));
        List<Review>r = ReviewRepository.getReviewsByTitle(resultSet.getInt(1));


        return new Series(resultSet.getInt(1), resultSet.getString(2),g,
                resultSet.getString(3), a, dir, resultSet.getInt(4), resultSet.getInt(5),
                resultSet.getInt(6), r);
    }


}
