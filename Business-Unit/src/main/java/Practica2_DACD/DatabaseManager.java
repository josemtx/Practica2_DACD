package Practica2_DACD;

import java.sql.*;

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
                    "tax REAL, " +
                    "latitude REAL, " +    // Nuevo: latitud
                    "longitude REAL)");   // Nuevo: longitud

            // Crear tabla Destinations
            statement.execute("CREATE TABLE IF NOT EXISTS Destinations (" +
                    "destination_id INTEGER PRIMARY KEY, " +
                    "hotel_name TEXT, " +
                    "hotel_key TEXT, " +
                    "check_in TEXT, " +
                    "check_out TEXT, " +
                    "average_rate REAL, " +
                    "currency TEXT, " +
                    "latitude REAL, " +
                    "longitude REAL, " +
                    "average_temperature REAL, " +
                    "probability_of_precipitation REAL)"
            );
        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }

    // Método para obtener la información del destino
    public DestinationInfo getDestinationInfo(String hotelName) {
        String sql = "SELECT hotel_name, average_rate, currency, average_temperature, probability_of_precipitation " +
                "FROM Destinations WHERE hotel_name = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, hotelName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Crear una nueva instancia de DestinationInfo con los valores obtenidos
                return new DestinationInfo(
                        rs.getString("hotel_name"),
                        rs.getDouble("average_rate"),
                        rs.getString("currency"),
                        rs.getDouble("average_temperature"),
                        rs.getDouble("probability_of_precipitation")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}