package Practica2_DACD;

import javax.jms.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.activemq.ActiveMQConnectionFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class FileCreator {
    private String brokerUrl;
    private String topic;

    public FileCreator(String brokerUrl, String topic) {
        this.brokerUrl = brokerUrl;
        this.topic = topic;
    }

    public void start() {
        ConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
        Connection connection = null;
        Session session = null;
        try {
            connection = factory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            connection.start();
            Destination destination = session.createTopic(topic);
            MessageConsumer consumer = session.createConsumer(destination);


            while (true) { // Replace with a more robust condition or signal
                Message message = consumer.receive();
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    String jsonPayload = textMessage.getText();
                    storeEvent(jsonPayload);
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            // Clean up
            closeSession(session);
            closeConnection(connection);
        }
    }

    private void closeSession(Session session) {
        try {
            if (session != null) {
                session.close();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void storeEvent(String jsonPayload) {
        // Deserialize the JSON payload to extract the 'ts' field
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> eventMap = gson.fromJson(jsonPayload, type);
        double ts = (double) eventMap.get("ts");
        Instant eventTime = Instant.ofEpochSecond((long) ts);

        // Format the event timestamp in the "yyyyMMdd" format
        String date = DateTimeFormatter.ofPattern("yyyyMMdd").withZone(ZoneId.of("UTC")).format(eventTime);

        // Create the directory and file based on the event timestamp
        String directoryPath = "eventstore/prediction.Weather/" + date;
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String filePath = directoryPath + "/" + date + ".events";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(jsonPayload);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
