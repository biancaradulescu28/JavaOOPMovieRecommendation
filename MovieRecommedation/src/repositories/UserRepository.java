package repositories;

import classes.cast.Actor;
import classes.title.Review;
import classes.user.User;
import classes.title.Title;
import database.DatabaseConfiguration;
import services.MainService;

import java.sql.*;
import java.util.List;

public class UserRepository {
    static MainService mainService = MainService.getInstance();
    public static void createTable(){
        String createTable = "CREATE TABLE IF NOT EXISTS USER " +
                "(username VARCHAR(100) PRIMARY KEY, " +
                "firstName VARCHAR(100) NOT NULL, " +
                "lastName VARCHAR(100) NOT NULL, " +
                "email VARCHAR(100) NOT NULL, " +
                "password VARCHAR(100) NOT NULL);";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            stmt.execute(createTable);
        }
        catch (SQLException e)
        {
            System.out.println("Error when creating UserTable!");
        }
    }

    //TODO: addData

    public static void insertUser(String username, String firstName, String lastName, String email, String password)
    {
        String user = "\"" + username + "\", \"" + firstName + "\", \"" + lastName +
                "\", \"" + email + "\", \"" + password + "\"";

        String insertUser = "INSERT INTO USER(username, firstName, lastName, email, password) VALUES(" + user + ");";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            stmt.executeUpdate(insertUser);
        }
        catch (SQLException e)
        {
            System.out.println("Error when inserting User!");
        }
    }

    public void displayUser()
    {
        String displayUser= "SELECT * FROM USER;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            boolean empty = true;
            ResultSet resultSet = stmt.executeQuery(displayUser);
            while (resultSet.next())
            {
                empty = false;
                System.out.println("Username: " + resultSet.getString(1));
                System.out.println("First name: " + resultSet.getString(2));
                System.out.println("Last name: " + resultSet.getString(3));
                System.out.println("Email: " + resultSet.getString(4));
                System.out.println("Password: " + resultSet.getString(5));
                System.out.println();
            }

            if (empty)
            {
                System.out.println("No existing users!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error when displaying users!");
        }
    }


    public static void transformDBToLists(){

        String selectAll = "SELECT * FROM USER;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement())
        {
            boolean empty = true;
            ResultSet resultSet = stmt.executeQuery(selectAll);
            while (resultSet.next())
            {
                empty = false;

                User u = new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), null);

                mainService.getUsers().add(u);

            }

            if (empty)
            {
                System.out.println("No existing users!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error when selecting users!");
        }

    }
    public static User getUserByUsername(String username)
    {
        String getUserByUsername = "SELECT * FROM USER WHERE username=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(getUserByUsername))
        {
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                return new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), null);
            }
            else {
                System.out.println("No username found with the specified username!");
                return null;
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error when getting user by username!");
            return null;
        }

    }

    public void changeProfile (String username, String firstName, String lastName, String password)
    {
        String changeProfile = "UPDATE USER SET firstName=?, lastName=?, password=? WHERE username=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(changeProfile))
        {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, username);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error when updating User!");
        }
    }

    public void deleteUserByUsername(String username)
    {
        String deleteUserByUsername = "DELETE FROM USER WHERE username=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteUserByUsername))
        {
            preparedStatement.setString(1, username);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error when deleting User!");
        }
    }

}
