package repositories;

import classes.cast.Actor;
import classes.cast.Director;
import classes.title.*;
import database.DatabaseConfiguration;
import services.MainService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository {
    static MainService mainService = MainService.getInstance();
    public static void createTable() {
        String createTable = "CREATE TABLE IF NOT EXISTS MOVIE " +
                "(id INT PRIMARY KEY, " +
                "releaseYear INT NOT NULL, " +
                "collection_id INT," +
                "director_id INT," +
                "FOREIGN KEY (collection_id) REFERENCES collection(id)," +
                "FOREIGN KEY (director_id) REFERENCES director(cast_id));";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTable);
        } catch (SQLException e) {
            System.out.println("Error when creating MovieTable: " + e.getMessage());
        }
    }

    //TODO: addData

    public static void insertMovie(int id, int releaseYear, Integer collection_id, int director_id)
    {
        String Movie = id + ", " + releaseYear + ", " + collection_id + ", " + director_id ;

        String insertMovie = "INSERT INTO MOVIE(id, releaseYear, collection_id, director_id) VALUES(" + Movie + ");";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            stmt.executeUpdate(insertMovie);
        }
        catch (SQLException e)
        {
            System.out.println("Error when inserting Movie!");
        }
    }

    public void displayMovie()
    {
        String displayMovie= "SELECT * FROM MOVIE;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            boolean empty = true;
            ResultSet resultSet = stmt.executeQuery(displayMovie);
            while (resultSet.next())
            {
                empty = false;
                System.out.println("id: " + resultSet.getInt(1));
                System.out.println("Release year: " + resultSet.getInt(2));
                System.out.println("CollectionId: " + resultSet.getInt(3));
                System.out.println("DirectorId: " + resultSet.getInt(4));
                System.out.println();
            }

            if (empty)
            {
                System.out.println("No existing Movies!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error when displaying Movies!");
        }
    }

    public static void transformDBToLists(){

        String selectAll = "SELECT * FROM MOVIE;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            boolean empty = true;
            ResultSet resultSet = stmt.executeQuery(selectAll);
            while (resultSet.next())
            {
                empty = false;
                Movie movie = getMovieById(resultSet.getInt(1));
                mainService.getMovies().add(movie);
            }

            if (empty)
            {
                System.out.println("No existing movies!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error when selecting movies!");
        }

    }

    public static List<Movie> getMoviesByCollection(int collection_id){

        List<Movie> movies = new ArrayList<>();

        String selectByCollection= "SELECT * FROM MOVIE WHERE collection_id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectByCollection))
        {
            preparedStatement.setInt(1, collection_id);
            boolean empty = true;
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                empty = false;
                Movie movie = getMovieById(resultSet.getInt(1));
                movies.add(movie);
            }

            if (empty)
            {
                System.out.println("No existing Movies for Collection!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error when selecting Movies for Collection!");
        }
        return movies;
    }

    public static Movie getMovieById(int id)
    {
        String getMovieById = "SELECT t.id, t.title, t.language, m.releaseYear, m.collection_id, m.director_id FROM title t, movie m WHERE t.id=m.id AND m.id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(getMovieById))
        {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return mapToMovie(resultSet);
            }
            return null;

        }
        catch (SQLException e)
        {
            System.out.println("Error when getting movie by id!");
            return null;
        }

    }

    public static void updateMovie (String title, String language, int director_id, int releaseYear, int collection_id, int id)
    {
        String updateMovie = "UPDATE MOVIE SET releaseYear=?, collection_id=?, director_id=? WHERE id=?";
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

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateMovie))
        {
            preparedStatement.setInt(1, releaseYear);
            preparedStatement.setInt(2, collection_id);
            preparedStatement.setInt(3, director_id);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error when updating Movie!");
        }
    }

    public static void deleteMovieById(int id)
    {
        String deleteMovieById = "DELETE FROM MOVIE WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteMovieById))
        {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error when deleting Movie!");
        }

        TitleRepository.deleteTitleById(id);
    }

    private static Movie mapToMovie(ResultSet resultSet) throws SQLException
    {
        Director dir = new Director();
        int dirId = resultSet.getInt(6);

        for(Director d: mainService.getDirectors()){
            if(d.getCastId() == dirId){
                dir = d;
                break;
            }
        }

        List<String>g = GenreRepository.getGenreForTitle(resultSet.getInt(1));
        List<Actor>a = PlayRepository.getPlayByTitle(resultSet.getInt(1));
        List<Review>r = ReviewRepository.getReviewsByTitle(resultSet.getInt(1));

        Movie m = new Movie(resultSet.getInt(1), resultSet.getString(2), g,
                resultSet.getString(3),a,dir, resultSet.getInt(4), r);
        return m;
    }


}
