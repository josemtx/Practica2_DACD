package Practica2_DACD;

import Practica2_DACD.model.Book;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ComparisonDataManager {
    private final DatabaseManager dbManager;
    private final List<Book> books; // Cambiado a una lista de objetos Book
    private int apiResponseIndex = 0; // Índice para rastrear la correspondencia con la lista de Books

    public ComparisonDataManager(DatabaseManager dbManager, List<Book> books) {
        this.dbManager = dbManager;
        this.books = books;
        System.out.println("Cantidad de libros cargados: " + books.size());
    }

    public void insertHotelRateData(String checkIn, String checkOut, String currency,
                                    String hotelCode, String hotelName, double rate, double tax,
                                    double latitude, double longitude) {
        String sql = "INSERT INTO HotelRates (check_in, check_out, currency, code, " +
                "name, rate, tax, latitude, longitude) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, checkIn);
            pstmt.setString(2, checkOut);
            pstmt.setString(3, currency);
            pstmt.setString(4, hotelCode);
            pstmt.setString(5, hotelName);
            pstmt.setDouble(6, rate);
            pstmt.setDouble(7, tax);
            pstmt.setDouble(8, latitude);   // Latitud
            pstmt.setDouble(9, longitude); // Longitud

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

                System.out.println("Procesando evento: apiResponseIndex = " + apiResponseIndex);

                if (apiResponseIndex >= books.size()) {
                    System.out.println("No hay más libros para procesar. apiResponseIndex = " + apiResponseIndex);
                    return; // Salir si no hay más libros para procesar
                }

                Book book = books.get(apiResponseIndex);
                System.out.println("Procesando libro: " + book.getName());

                for (int i = 0; i < rates.length(); i++) {
                    JSONObject rateInfo = rates.getJSONObject(i);
                    String hotelCode = rateInfo.getString("code");
                    double rate = rateInfo.getDouble("rate");
                    double tax = rateInfo.getDouble("tax");

                    System.out.println("Insertando datos para hotelCode: " + hotelCode + ", rate: " + rate + ", tax: " + tax);
                    insertHotelRateData(String.valueOf(checkIn), String.valueOf(checkOut), currency, hotelCode, book.getName(), rate, tax, book.getLocation().getLat(), book.getLocation().getLon());
                }

                apiResponseIndex++; // Incrementar el índice después de procesar cada respuesta de la API
            }
        } catch (Exception e) {
            System.out.println("Error processing comparison event: " + e.getMessage());
        }
    }
}