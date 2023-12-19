package Practica2_DACD;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileCreator {
    private final String brokerUrl;

    public FileCreator(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

    public void startListeningToTopics(String... topics) {
        ConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
        Connection connection = null;
        Session session = null;
        try {
            connection = factory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            connection.start();

            for (String topic : topics) {
                Destination destination = session.createTopic(topic);
                MessageConsumer consumer = session.createConsumer(destination);
                consumer.setMessageListener(message -> {
                    // Tu código de procesamiento aquí
                });
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            // Cerrar los recursos manualmente si no son AutoCloseable
            try {
                if (session != null) {
                    session.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    private void storeRawEvent(String jsonPayload, String topic) {
        // Create the directory and file based on the topic
        String directoryPath = String.format("eventstore/raw/%s", topic);
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String filePath = String.format("%s/raw.events", directoryPath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(jsonPayload);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}