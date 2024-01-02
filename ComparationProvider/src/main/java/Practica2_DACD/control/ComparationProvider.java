package Practica2_DACD.control;

import Practica2_DACD.model.Book;
import Practica2_DACD.model.Rates;
import com.google.gson.*;

import javax.jms.JMSException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ComparationProvider {

    private final List<Book> bookList;
    private final ActiveMQSender activeMQSender;
    private final ActiveMQConsumer activeMQConsumer;

    public ComparationProvider(List<Book> bookList, String brokerUrl) throws JMSException {
        this.bookList = bookList;
        this.activeMQSender = new ActiveMQSender(brokerUrl);
        this.activeMQConsumer = new ActiveMQConsumer(brokerUrl, "comparison.Results", "datalake");
    }

    public void fetchAndSendComparationData() throws IOException {
        List<Rates> ratesList = fetchComparationData();
        Gson gson = new Gson();
        String json = gson.toJson(ratesList);

        // Enviar la información al topic 'comparison.Results'
        activeMQSender.sendMessage("comparison.Results", json);
        System.out.println("Datos enviados al topic 'comparison.Results' con éxito.");
    }

    private List<Rates> fetchComparationData() throws IOException {
        List<Rates> ratesList = new ArrayList<>();
        for (Book book : bookList) {
            String response = new Response().sendGetRequest(book.getHotel_Key(), book.getCheckIn(), book.getCheckOut());
            System.out.println("Response from API: " + response); // Imprimir la respuesta de la API
            JsonObject jsonObject = convertResponseToJson(response);
            processComparationData(jsonObject, ratesList);
        }
        return ratesList;
    }

    private JsonObject convertResponseToJson(String response) {
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(response).getAsJsonObject();
    }

    private void processComparationData(JsonObject jsonObject, List<Rates> ratesList) {
        JsonObject result = jsonObject.getAsJsonObject("result");
        if (result != null) {
            JsonArray rates = result.getAsJsonArray("rates");
            for (JsonElement element : rates) {
                JsonObject rateObj = element.getAsJsonObject();
                String code = rateObj.get("code").getAsString();
                String name = rateObj.get("name").getAsString();
                double rate = rateObj.get("rate").getAsDouble();
                double tax = rateObj.get("tax").getAsDouble();

                Rates rateData = new Rates(code, name, rate, tax);
                ratesList.add(rateData);
            }
        }
    }
}
