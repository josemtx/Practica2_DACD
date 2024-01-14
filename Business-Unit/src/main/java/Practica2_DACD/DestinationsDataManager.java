package Practica2_DACD;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DestinationsDataManager {
    private final DatabaseManager dbManager;

    public DestinationsDataManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public void updateDestinationsData() {
        String sql = "INSERT INTO Destinations (hotel_name, hotel_key, check_in, check_out, average_rate, currency, latitude, longitude, average_temperature, probability_of_precipitation) " +
                "SELECT " +
                "H.name AS hotel_name, " + // Asegúrate de seleccionar el nombre del hotel
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
                "Weather W ON H.latitude = W.latitude AND H.longitude = W.longitude " +
                "WHERE W.prediction_time BETWEEN H.check_in AND H.check_out " +
                "GROUP BY H.name, H .code, H.check_in, H.check_out, H.currency, W.latitude, W.longitude";

        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Datos de destinos actualizados. Filas afectadas: " + rowsAffected);
        } catch (SQLException e) {
            System.out.println("Error actualizando los datos de destinos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}