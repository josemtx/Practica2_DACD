package Practica2_DACD.control;

import Practica2_DACD.model.Location;
import Practica2_DACD.model.Weather;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PredictionProvider {

    private final List<Location> listLocation;

    public PredictionProvider(List<Location> locations) {
        this.listLocation = locations;
    }

    public List<Weather> fetchWeatherData() throws IOException, ParseException {
        List<Weather> weatherDataList = new ArrayList<>();
        for (Location location : listLocation) {
            String response = new Response().sendGetRequest(location.getLat(), location.getLon(), "fe649f1944e892e7eb8d4a735edd3429", "metric");
            JsonObject jsonObject = convertResponseToJson(response);
            processWeatherData(jsonObject, location, weatherDataList);
        }
        return weatherDataList;
    }

    private JsonObject convertResponseToJson(String response) {
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(response).getAsJsonObject();
    }

    private void processWeatherData(JsonObject jsonObject, Location location, List<Weather> weatherDataList) throws ParseException {
        JsonArray list = jsonObject.getAsJsonArray("list");
        for (JsonElement element : list) {
            String dt_txt = element.getAsJsonObject().get("dt_txt").getAsString();
            if (isTargetHour(dt_txt)) {
                JsonObject jsonObjectMain = element.getAsJsonObject().get("main").getAsJsonObject();
                double temperature = jsonObjectMain.get("temp").getAsDouble();
                double pop = element.getAsJsonObject().get("pop").getAsDouble();
                double humidity = jsonObjectMain.get("humidity").getAsDouble();
                double clouds = element.getAsJsonObject().getAsJsonObject("clouds").get("all").getAsDouble();
                double windSpeed = element.getAsJsonObject().getAsJsonObject("wind").get("speed").getAsDouble();
                Instant predictionTime = Instant.ofEpochSecond(element.getAsJsonObject().get("dt").getAsLong());

                // Obtener el instante actual en UTC
                ZonedDateTime utcNow = ZonedDateTime.now(ZoneId.of("UTC"));

                // Convertir a Timestamp
                Timestamp ts = Timestamp.from(utcNow.toInstant());

                String ss = "OpenWeather";
                Weather weatherData = new Weather(predictionTime, ts, ss, temperature, pop, humidity, clouds, windSpeed, location);
                weatherDataList.add(weatherData);
            }
        }
    }

    private boolean isTargetHour(String dt_txt) throws ParseException {
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dt_txt);
        return hourFormat.format(date).equals("12:00:00");
    }


}
