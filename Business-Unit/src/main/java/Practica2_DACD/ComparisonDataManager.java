package Practica2_DACD;

import Practica2_DACD.model.Book;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComparisonDataManager {
    private final DatabaseManager dbManager;
    private final Map<String, Book> bookMap;

    public ComparisonDataManager(DatabaseManager dbManager, List<Book> books) {
        this.dbManager = dbManager;
        this.bookMap = new HashMap<>();

        // Llenar el mapa con los libros
        for (Book book : books) {
            this.bookMap.put(book.getHotelKey(), book);
        }
    }

    public void insertHotelRateData(String checkIn, String checkOut, String currency,
                                    String hotelCode, String hotelName, double rate, double tax,
                                    double latitude, double longitude) {
        String sql = "INSERT INTO HotelRates (check_in, check_out, currency, code, " +
                "name, rate, tax, hotel_name, latitude, longitude) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, checkIn);
            pstmt.setString(2, checkOut);
            pstmt.setString(3, currency);
            pstmt.setString(4, hotelCode);
            pstmt.setString(5, hotelName);
            pstmt.setDouble(6, rate);
            pstmt.setDouble(7, tax);
            pstmt.setString(8, hotelName);  // Nombre del hotel
            pstmt.setDouble(9, latitude);   // Latitud
            pstmt.setDouble(10, longitude); // Longitud

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting hotel rate data: " + e.getMessage());
        }
    }

    public void handleComparisonEvent(String jsonEvent) {
        try {
            JSONObject event = new JSONObject(jsonEvent);
            if (event.has("result") && !event.isNull("result")) {
                JSONObject result = event.getJSONObject("result");
                String checkIn = result.getString("chk_in");
                String checkOut = result.getString("chk_out");
                String currency = result.getString("currency");
                JSONArray rates = result.getJSONArray("rates");

                for (int i = 0; i < rates.length(); i++) {
                    JSONObject rateInfo = rates.getJSONObject(i);
                    String hotelCode = rateInfo.getString("code");
                    String hotelName = rateInfo.getString("name");
                    double rate = rateInfo.getDouble("rate");
                    double tax = rateInfo.getDouble("tax");

                    // Obtener la ubicación del libro correspondiente al código del hotel
                    Book book = bookMap.get(hotelCode);
                    double latitude = book.getLocation().getLat();
                    double longitude = book.getLocation().getLon();

                    insertHotelRateData(checkIn, checkOut, currency, hotelCode, hotelName, rate, tax, latitude, longitude);
                }
            }
        } catch (Exception e) {
            System.out.println("Error processing comparison event: " + e.getMessage());
        }
    }
}
