package Practica2_DACD.control;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ActiveMQConsumer implements MessageListener {
    private final String fileDirectory;

    public ActiveMQConsumer(String brokerUrl, String topicName, String fileDirectory) throws JMSException {
        this.fileDirectory = fileDirectory;
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

                // Obtener la fecha y hora actual para el nombre del archivo
                String formattedDate = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now());
                String fileName = "datalake/comparison.Results/" + formattedDate + ".events";

                // Escribir el mensaje en el archivo
                writeToFile(fileName, text);

            } catch (JMSException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeToFile(String fileName, String content) throws IOException {
        File file = new File(fileName);
        file.getParentFile().mkdirs(); // Crea directorios si no existen
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(content);
            writer.newLine();
        }
    }
}