package Practica2_DACD;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class EventSubscriber {
    private Session session;
    private Connection connection;
    private Map<String, MessageConsumer> consumers;
    private String rootDirectory;

    // Constructor actualizado
    public EventSubscriber(String brokerUrl, String clientID, String rootDirectory) throws JMSException {
        this.rootDirectory = rootDirectory;
        this.consumers = new HashMap<>();
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        Connection connection = connectionFactory.createConnection();

        // Establecer clientID en la conexión
        connection.setClientID(clientID);

        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public void subscribeToWeatherTopic(String topicName, Consumer<String> eventHandler) throws JMSException {
        Topic topic = session.createTopic(topicName);
        MessageConsumer consumer = session.createDurableSubscriber(topic, "DurableConsumer_" + topicName);
        consumer.setMessageListener(message -> {
            try {
                if (message instanceof TextMessage) {
                    String text = ((TextMessage) message).getText();
                    eventHandler.accept(text);
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
    }

    public void subscribeToComparisonTopic(String topicName, Consumer<String> eventHandler) throws JMSException {
        Topic topic = session.createTopic(topicName);
        MessageConsumer consumer = session.createDurableSubscriber(topic, "DurableConsumer_" + topicName);
        consumer.setMessageListener(message -> {
            try {
                if (message instanceof TextMessage) {
                    String text = ((TextMessage) message).getText();
                    eventHandler.accept(text);
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
    }

    public void close() throws JMSException {
        if (session != null) {
            session.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}