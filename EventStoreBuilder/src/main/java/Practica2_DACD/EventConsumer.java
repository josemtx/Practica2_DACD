package Practica2_DACD;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.jms.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EventConsumer {

    private Session session;
    private Map<String, MessageConsumer> consumers;
    private String rootDirectory;

    public EventConsumer(String brokerUrl, String clientID, String rootDirectory) throws JMSException {
        this.rootDirectory = rootDirectory;
        this.consumers = new HashMap<>();
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        Connection connection = connectionFactory.createConnection();

        // Establecer clientID en la conexión
        connection.setClientID(clientID);

        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public void subscribeToTopic(String topicName) throws JMSException {
        Topic topic = session.createTopic(topicName);
        MessageConsumer consumer = session.createDurableSubscriber(topic, "DurableConsumer_" + topicName);
        consumer.setMessageListener(message -> {
            try {
                if (message instanceof TextMessage) {
                    String json = ((TextMessage) message).getText();
                    processMessage(json, topicName);
                }
            } catch (JMSException | IOException e) {
                e.printStackTrace();
            }
        });
        consumers.put(topicName, consumer);
    }

    private void processMessage(String json, String topicName) throws IOException {
        if ("comparison.Results" .equals(topicName)) {
            // Tratamiento especial para 'comparison.Results'
            String fileName = constructFileNameForComparisonResults(json);
            appendToFile(fileName, json);
        } else {
            // Tratamiento para otros topics
            if (json != null && json.trim().startsWith("{")) {
                // Manejo de objeto JSON
                JSONObject jsonObject = new JSONObject(json);
                String ts = jsonObject.optString("ts");
                String ss = jsonObject.optString("ss");

                if (!ts.isEmpty() && !ss.isEmpty()) {
                    String fileName = constructFileName(topicName, ss, ts);
                    appendToFile(fileName, json);
                }
            } else if (json != null && json.trim().startsWith("[")) {
                // Manejo de arreglo JSON
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    // Aquí podrías manejar estos objetos como necesites
                }
            } else {
                System.err.println("Invalid JSON message: " + json);
            }
        }
    }

    private String constructFileNameForComparisonResults(String json) {
        // Usar un timestamp actual para el nombre del archivo
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return rootDirectory + "/datalake/eventstore/comparison.Results/" + timestamp + ".events";
    }

    private String constructFileName(String topic, String ss, String ts) throws IOException {
        SimpleDateFormat sourceFormat = new SimpleDateFormat("MMM dd, yyyy, h:mm:ss a", Locale.ENGLISH);
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date = sourceFormat.parse(ts);
            String formattedDate = targetFormat.format(date);
            return rootDirectory + "/datalake/eventstore/" + topic + "/" + ss + "/" + formattedDate + ".events";
        } catch (ParseException e) {
            throw new IOException("Error parsing date: " + ts, e);
        }
    }

    private void appendToFile(String fileName, String content) throws IOException {
        File file = new File(fileName);
        file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(content);
            writer.newLine();
        }
    }
}
