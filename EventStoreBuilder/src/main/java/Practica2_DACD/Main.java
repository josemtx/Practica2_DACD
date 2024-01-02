package Practica2_DACD;

import javax.jms.JMSException;

public class Main {
    public static void main(String[] args) throws JMSException {
        String brokerUrl = "tcp://localhost:61616"; // Ajusta esto a la URL de tu broker
        String clientID = "uniqueClientID"; // Establece un clientID único para este suscriptor
        String rootDirectory = args.length > 0 ? args[0] : "."; // Directorio raíz por defecto si no se proporciona

        EventConsumer consumer = new EventConsumer(brokerUrl, clientID, rootDirectory);
        consumer.subscribeToTopic("prediction.Weather");
        consumer.subscribeToTopic("comparison.Results");
        // Aquí puedes añadir más suscripciones a otros tópicos si es necesario
    }
}