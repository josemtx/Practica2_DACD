package Practica2_DACD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager(String dbPath) {
        try {
            // Asegúrate de que el driver JDBC de SQLite se ha cargado
            Class.forName("org.sqlite.JDBC");
            // Establece la conexión con la base de datos SQLite
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        } catch (SQLException e) {
            System.out.println("Error connecting to SQLite database: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void createTables() {
        if (connection == null) {
            System.out.println("Database connection is not established.");
            return;
        }

        try (Statement statement = connection.createStatement()) {
            // Crear tabla Weather
            statement.execute("CREATE TABLE IF NOT EXISTS Weather (" +
                    "weather_id INTEGER PRIMARY KEY, " +
                    "prediction_time TEXT, " +
                    "timestamp TEXT, " +
                    "source TEXT, " +
                    "temperature REAL, " +
                    "pop REAL, " +
                    "humidity REAL, " +
                    "clouds INTEGER, " +
                    "wind_speed REAL, " +
                    "latitude REAL, " +
                    "longitude REAL)");

            // Crear tabla HotelRates
            statement.execute("CREATE TABLE IF NOT EXISTS HotelRates (" +
                    "rate_id INTEGER PRIMARY KEY, " +
                    "check_in TEXT, " +
                    "check_out TEXT, " +
                    "currency TEXT, " +
                    "code TEXT, " +
                    "name TEXT, " +
                    "rate REAL, " +
                    "tax REAL)");

            // Crear tabla Destinations
            statement.execute("CREATE TABLE IF NOT EXISTS Destinations (" +
                    "destination_id INTEGER PRIMARY KEY, " +
                    "check_in TEXT, " +
                    "check_out TEXT, " +
                    "name TEXT, " +
                    "temperature REAL, " +
                    "pop REAL, " +
                    "rate REAL, " +
                    "tax REAL, " +
                    "latitude REAL, " +
                    "longitude REAL)");
        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }

    // Métodos adicionales para insertar y consultar datos irían aquí
}
