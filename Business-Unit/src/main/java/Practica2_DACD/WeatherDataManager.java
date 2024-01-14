package Practica2_DACD;

import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WeatherDataManager {
    private final DatabaseManager dbManager;

    public WeatherDataManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public void insertWeatherData(String predictionTime, String timestamp, String source,
                                  double temperature, double pop, double humidity, int clouds,
                                  double windSpeed, double latitude, double longitude) {
        String sql = "INSERT INTO Weather (prediction_time, timestamp, source, temperature, " +
                "pop, humidity, clouds, wind_speed, latitude, longitude) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, predictionTime);
            pstmt.setString(2, timestamp);
            pstmt.setString(3, source);
            pstmt.setDouble(4, temperature);
            pstmt.setDouble(5, pop);
            pstmt.setDouble(6, humidity);
            pstmt.setInt(7, clouds);
            pstmt.setDouble(8, windSpeed);
            pstmt.setDouble(9, latitude);
            pstmt.setDouble(10, longitude);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting weather data: " + e.getMessage());
        }
    }

    public void handleWeatherEvent(String jsonEvent) {
        // Parse the JSON event to extract weather data
        // Assuming jsonEvent is a JSON string that contains the weather event information
        JSONObject event = new JSONObject(jsonEvent);
        String predictionTime = event.getString("predictionTime");
        String timestamp = event.getString("ts");
        String source = event.getString("ss");
        double temperature = event.getDouble("temp");
        double pop = event.getDouble("pop");
        double humidity = event.getDouble("humidity");
        int clouds = event.getInt("clouds");
        double windSpeed = event.getDouble("windSpeed");
        JSONObject location = event.getJSONObject("Location");
        double latitude = location.getDouble("lat");
        double longitude = location.getDouble("lon");

        // Insert the data into the database
        insertWeatherData(predictionTime, timestamp, source, temperature, pop, humidity, clouds, windSpeed, latitude, longitude);
    }
}
