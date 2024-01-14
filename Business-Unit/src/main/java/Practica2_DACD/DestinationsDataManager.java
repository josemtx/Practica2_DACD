package Practica2_DACD;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DestinationsDataManager {
    private final DatabaseManager dbManager;

    public DestinationsDataManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public void updateDestinationsData() {
        String sql = "INSERT INTO Destinations (hotel_key, check_in, check_out, average_rate, currency, latitude, longitude, average_temperature, probability_of_precipitation) " +
                "SELECT " +
                "H.code AS hotel_key, " +
                "H.check_in, " +
                "H.check_out, " +
                "AVG(H.rate) AS average_rate, " +
                "H.currency, " +
                "W.latitude, " +
                "W.longitude, " +
                "AVG(W.temperature) AS average_temperature, " +
                "AVG(W.pop) AS probability_of_precipitation " +
                "FROM " +
                "HotelRates H " +
                "JOIN " +
                "Weather W ON W.prediction_time BETWEEN H.check_in AND H.check_out " +
                "GROUP BY " +
                "H.code, " +
                "H.check_in, " +
                "H.check_out, " +
                "H.currency, " +
                "W.latitude, " +
                "W.longitude";

        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error actualizando los datos de destinos: " + e.getMessage());
        }
    }
}