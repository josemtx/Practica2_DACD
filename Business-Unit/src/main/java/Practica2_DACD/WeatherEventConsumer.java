package Practica2_DACD;

import org.json.JSONObject;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class WeatherEventConsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                String json = ((TextMessage) message).getText();
                JSONObject jsonObject = new JSONObject(json);

                // Extraer los campos relevantes
                String predictionTime = jsonObject.getString("predictionTime");
                String ts = jsonObject.getString("ts");
                String ss = jsonObject.getString("ss");
                double temp = jsonObject.getDouble("temp");
                double pop = jsonObject.getDouble("pop");
                int humidity = jsonObject.getInt("humidity");
                int clouds = jsonObject.getInt("clouds");
                double windSpeed = jsonObject.getDouble("windSpeed");
                JSONObject location = jsonObject.getJSONObject("Location");
                double lat = location.getDouble("lat");
                double lon = location.getDouble("lon");

                // Aquí puedes procesar estos datos como necesites
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
