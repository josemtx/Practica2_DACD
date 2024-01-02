package Practica2_DACD.control;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

public class EventStoreBuilder implements MessageListener {

    public EventStoreBuilder(String brokerUrl, String topicName) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic(topicName);
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(this);
    }


    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                String text = ((TextMessage) message).getText();

                // Deserialize the JSON payload to extract the 'ts' field
                Gson gson = new Gson();
                Type type = new TypeToken<Map<String, Object>>() {
                }.getType();
                Map<String, Object> eventMap = gson.fromJson(text, type);
                String tsString = (String) eventMap.get("ts");

                // Parse the 'ts' field as an Instant
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy, h:mm:ss a", Locale.ENGLISH);
                LocalDateTime dateTime = LocalDateTime.parse(tsString, formatter);
                Instant ts = dateTime.atZone(ZoneId.systemDefault()).toInstant();

                String formattedDate = DateTimeFormatter.ofPattern("yyyyMMdd")
                        .withZone(ZoneId.systemDefault())
                        .format(ts);

                File directory = new File("eventstore/prediction.Weather/" + ts.getEpochSecond());
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                File file = new File(directory, formattedDate + ".events");
                try (FileWriter writer = new FileWriter(file, true)) {
                    writer.write(text + "\n");
                }
            } catch (JMSException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}