package repositories;

import database.DatabaseConfiguration;
import classes.cast.Actor;
import services.MainService;

import java.sql.*;
import java.util.List;

public class ActorRepository {

    static MainService mainService = MainService.getInstance();
    public static void createTable(){
        String createTable = "CREATE TABLE IF NOT EXISTS ACTOR " +
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
            System.out.println("Error when creating ActorTable!");
        }
    }

    //TODO: addData

    public static void insertActor(int cast_id, String firstName, String lastName)
    {
        String Actor = cast_id + ",\"" + firstName + "\", \"" + lastName + "\"";

        String insertActor = "INSERT INTO ACTOR(cast_id, firstName, lastName) VALUES(" + Actor + ");";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            stmt.executeUpdate(insertActor);
        }
        catch (SQLException e)
        {
            System.out.println("Error when inserting Actor!");
        }
    }

    public void displayActor()
    {
        String displayActor= "SELECT * FROM ACTOR;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            boolean empty = true;
            ResultSet resultSet = stmt.executeQuery(displayActor);
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
                System.out.println("No existing actors!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error when displaying Actors!");
        }
    }

    public static void transformDBToLists(){

        String selectAll = "SELECT * FROM ACTOR;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            boolean empty = true;
            ResultSet resultSet = stmt.executeQuery(selectAll);
            while (resultSet.next())
            {
                empty = false;
                Actor actor = new Actor(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
                mainService.getActors().add(actor);
            }

            if (empty)
            {
                System.out.println("No existing actors!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error when selecting Actors!");
        }

    }


    public static Actor getActorById(int cast_id)
    {
        String getActorById = "SELECT * FROM ACTOR WHERE cast_id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(getActorById))
        {
            preparedStatement.setInt(1, cast_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Actor actor = new Actor(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
                return actor;
            } else {
                System.out.println("No actor found with the specified id!");
                return null;
            }

        }
        catch (SQLException e)
        {
            System.out.println("Error when getting actor by id!" + e + "!!!!");
            return null;
        }

    }

    public void updateActor (String firstName, String lastName, int cast_id)
    {
        String updateActor = "UPDATE ACTOR SET firstName=?, lastName=? WHERE cast_id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateActor))
        {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, cast_id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error when updating Actor!");
        }
    }

    public void deleteActorById(int cast_id)
    {
        String deleteActorById = "DELETE FROM ACTOR WHERE cast_id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteActorById))
        {
            preparedStatement.setInt(1, cast_id);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error when deleting Actor!");
        }
    }

}
