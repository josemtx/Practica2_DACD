package Practica2_DACD.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Response {
    public String sendGetRequest(String hotel_key, LocalDate checkIn, LocalDate checkOut) throws IOException {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedCheckIn = checkIn.format(dateFormat);
        String formattedCheckOut = checkOut.format(dateFormat);

        String apiUrl = "https://data.xotelo.com/api/rates";
        String queryString = "hotel_key=" + URLEncoder.encode(hotel_key, StandardCharsets.UTF_8) +
                "&chk_in=" + URLEncoder.encode(formattedCheckIn, StandardCharsets.UTF_8) +
                "&chk_out=" + URLEncoder.encode(formattedCheckOut, StandardCharsets.UTF_8);

        URL url = new URL(apiUrl + "?" + queryString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            connection.disconnect();

            return response.toString();
        } else {
            throw new IOException("HTTP GET request failed with response code: " + responseCode);
        }
    }
}