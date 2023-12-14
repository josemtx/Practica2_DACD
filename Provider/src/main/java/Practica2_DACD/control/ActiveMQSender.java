package Practica2_DACD.control;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ActiveMQSender {
    private String brokerUrl;
    private final ConnectionFactory connectionFactory;

    public ActiveMQSender(String brokerUrl) {
        this.brokerUrl = brokerUrl;
        this.connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
    }

    public void sendMessage(String topic, String messageText) {
        Connection connection = null;
        Session session = null;
        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            connection.start();
            Destination destination = session.createTopic(topic);
            MessageProducer producer = session.createProducer(destination);

            TextMessage message = session.createTextMessage(messageText);
            producer.send(message);

            producer.close();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
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
}
