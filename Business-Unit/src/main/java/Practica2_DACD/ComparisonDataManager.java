package Practica2_DACD;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ComparisonDataManager {
    private DatabaseManager dbManager;

    public ComparisonDataManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public void insertHotelRateData(String checkIn, String checkOut, String currency,
                                    String hotelCode, String hotelName, double rate, double tax) {
        String sql = "INSERT INTO HotelRates (check_in, check_out, currency, code, " +
                "name, rate, tax) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, checkIn);
            pstmt.setString(2, checkOut);
            pstmt.setString(3, currency);
            pstmt.setString(4, hotelCode);
            pstmt.setString(5, hotelName);
            pstmt.setDouble(6, rate);
            pstmt.setDouble(7, tax);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting hotel rate data: " + e.getMessage());
        }
    }

    public void handleComparisonEvent(String jsonEvent) {
        // Parse the JSON event to extract hotel rates data
        // Assuming jsonEvent is a JSON string that contains the comparison event information
        JSONObject event = new JSONObject(jsonEvent);
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

            // Insert the data into the database
            insertHotelRateData(checkIn, checkOut, currency, hotelCode, hotelName, rate, tax);
        }
    }
}
