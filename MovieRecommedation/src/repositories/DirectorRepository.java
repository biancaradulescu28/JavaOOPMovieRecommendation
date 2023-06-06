package repositories;

import classes.cast.Director;
import database.DatabaseConfiguration;
import classes.cast.Director;
import services.MainService;

import java.sql.*;
import java.util.List;

public class DirectorRepository {
    static MainService mainService = MainService.getInstance();

    private static DirectorRepository DirectorRepository;

    public static DirectorRepository getInstance(){
        if(DirectorRepository==null){
            DirectorRepository = new DirectorRepository();
        }
        return DirectorRepository;
    }

    public static void createTable(){
        String createTable = "CREATE TABLE IF NOT EXISTS DIRECTOR " +
                "(cast_id INT PRIMARY KEY, " +
                "firstName VARCHAR(100), " +
                "lastName VARCHAR(100));";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            stmt.execute(createTable);
        }
        catch (SQLException e)
        {
            System.out.println("Error when creating DirectorTable!");
        }
    }

    //TODO: addData

    public static void insertDirector(int cast_id, String firstName, String lastName)
    {
        String Director = cast_id + ",\"" + firstName + "\", \"" + lastName + "\"";

        String insertDirector = "INSERT INTO DIRECTOR(cast_id, firstName, lastName) VALUES(" + Director + ");";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            stmt.executeUpdate(insertDirector);
        }
        catch (SQLException e)
        {
            System.out.println("Error when inserting Director!");
        }
    }

    public void displayDirector()
    {
        String displayDirector= "SELECT * FROM DIRECTOR;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            boolean empty = true;
            ResultSet resultSet = stmt.executeQuery(displayDirector);
            while (resultSet.next())
            {
                empty = false;
                System.out.println("cast_id: " + resultSet.getInt(1));
                System.out.println("First name: " + resultSet.getString(2));
                System.out.println("Last name: " + resultSet.getString(3));
                System.out.println();
            }

            if (empty)
            {
                System.out.println("No existing Directors!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error!");
        }
    }

    public static void transformDBToLists(){

        String selectAll = "SELECT * FROM DIRECTOR;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            boolean empty = true;
            ResultSet resultSet = stmt.executeQuery(selectAll);
            while (resultSet.next())
            {
                empty = false;
                Director Director = new Director(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
                mainService.getDirectors().add(Director);
            }

            if (empty)
            {
                System.out.println("No existing Directors!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error when displaying Directors!");
        }

    }

    public Director getDirectorById(int cast_id)
    {
        String getDirectorById = "SELECT * FROM DIRECTOR WHERE cast_id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(getDirectorById))
        {
            preparedStatement.setInt(1, cast_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Director(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
            }
            else {
                System.out.println("No director found with the specified id!");
                return null;
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error when getting director by id!");
            return null;
        }

    }

    public void updateDirector (String firstName, String lastName, int cast_id)
    {
        String updateDirector = "UPDATE DIRECTOR SET firstName=?, lastName=? WHERE cast_id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateDirector))
        {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, cast_id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error when updating Director!");
        }
    }

    public void deleteDirectorById(int cast_id)
    {
        String deleteDirectorById = "DELETE FROM DIRECTOR WHERE cast_id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteDirectorById))
        {
            preparedStatement.setInt(1, cast_id);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error when deleting Director!");
        }
    }

}
