package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfiguration
{

    private static final String DB_URL = "jdbc:mysql://localhost:3306/title_recommendations";
    private static final String USER = "root";
    private static final String PASSWORD = "1234susha";
    private static Connection databaseConnection;

    private DatabaseConfiguration() { }

    public static synchronized Connection getDatabaseConnection()
    {
        try
        {
            if (databaseConnection == null || databaseConnection.isClosed())
            {
                databaseConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error when opening the database!");
        }
        return databaseConnection;
    }

    public static void closeDatabaseConnection()
    {
        try
        {
            if (databaseConnection != null && !databaseConnection.isClosed())
            {
                databaseConnection.close();
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error when closing the database!");
        }
    }
}