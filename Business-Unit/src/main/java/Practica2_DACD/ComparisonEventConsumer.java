package Practica2_DACD;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ComparisonEventConsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                String json = ((TextMessage) message).getText();
                JSONObject jsonObject = new JSONObject(json);
                JSONObject result = jsonObject.getJSONObject("result");

                // Extraer los campos relevantes
                String chkIn = result.getString("chk_in");
                String chkOut = result.getString("chk_out");
                String currency = result.getString("currency");
                JSONArray rates = result.getJSONArray("rates");

                for (int i = 0; i < rates.length(); i++) {
                    JSONObject rate = rates.getJSONObject(i);
                    String code = rate.getString("code");
                    String name = rate.getString("name");
                    double rateValue = rate.getDouble("rate");
                    int tax = rate.getInt("tax");

                    // Procesar cada tarifa como necesites
                }

                // Opcional: Utilizar el timestamp si es relevante
                long timestamp = jsonObject.getLong("timestamp");

                // Procesar estos datos como necesites
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
