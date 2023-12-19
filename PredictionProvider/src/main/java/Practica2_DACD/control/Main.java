package Practica2_DACD.control;

import Practica2_DACD.model.Location;
import Practica2_DACD.model.Weather;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.jms.JMSException;
import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        // Crear una instancia de EventStoreBuilder
        EventStoreBuilder builder;
        try {
            builder = new EventStoreBuilder("tcp://localhost:61616", "prediction.Weather");
        } catch (JMSException e) {
            e.printStackTrace();
            return;
        }

        List<Location> locations = Arrays.asList(
                new Location(27.99549, -15.41765),
                new Location(28.463850790803008, -16.25097353346818),
                new Location(28.50047229032077, -16.25097353346818),
                new Location(28.965080860301025, -13.556148106209083),
                new Location(29.23141101200906, -13.503131221117982),
                new Location(27.809920552606453, -17.91474223115781),
                new Location(28.094369991798228, -17.109467831251514),
                new Location(28.684160726614596, -17.76582062032028)
        );

        // Crear una instancia de OpenWeatherMapProvider
        PredictionProvider provider = new PredictionProvider(locations);

        // Obtener la lista de objetos Weather
        List<Weather> weatherDataList = provider.fetchWeatherData();

        // Crear una instancia de Gson y registrar el TypeAdapter
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantAdapter())
                .create();

        // Crear una instancia de ActiveMQSender
        ActiveMQSender activeMQSender = new ActiveMQSender("tcp://localhost:61616");

        // Serializar cada objeto Weather a JSON y enviarlo al broker
        for (Weather weather : weatherDataList) {
            String json = gson.toJson(weather);
            System.out.println(json);

            // Enviar mensaje al topic 'prediction.Weather'
            activeMQSender.sendMessage("prediction.Weather", json);
        }
    }
}