package Practica2_DACD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DestinationsDataManager {
    private DatabaseManager dbManager;

    public DestinationsDataManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public void updateDestinationsData() {
        // Suponiendo que la lógica para seleccionar los registros relevantes de las tablas Weather y HotelRates está definida aquí
        String sql = "SELECT W.temperature, W.pop, H.rate, H.tax, H.name, H.currency, W.latitude, W.longitude " +
                "FROM Weather W INNER JOIN HotelRates H ON W.latitude = H.latitude AND W.longitude = H.longitude " +
                "WHERE W.latitude IS NOT NULL AND W.longitude IS NOT NULL";

        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                insertIntoDestinations(
                        rs.getString("name"),
                        rs.getDouble("temperature"),
                        rs.getDouble("pop"),
                        rs.getDouble("rate"),
                        rs.getDouble("tax"),
                        rs.getString("currency"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error updating destinations data: " + e.getMessage());
        }
    }

    private void insertIntoDestinations(String hotelName, double temperature, double pop,
                                        double rate, double tax, String currency,
                                        double latitude, double longitude) {
        String sql = "INSERT INTO Destinations (name, temperature, pop, rate, tax, currency, latitude, longitude) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, hotelName);
            pstmt.setDouble(2, temperature);
            pstmt.setDouble(3, pop);
            pstmt.setDouble(4, rate);
            pstmt.setDouble(5, tax);
            pstmt.setString(6, currency);
            pstmt.setDouble(7, latitude);
            pstmt.setDouble(8, longitude);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting into Destinations: " + e.getMessage());
        }
    }
}
