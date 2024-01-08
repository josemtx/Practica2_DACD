package Practica2_DACD;

import javax.jms.JMSException;

public class Main {
    public static void main(String[] args) {
        // Configurar la ruta de la base de datos SQLite
        String dbPath = "datamart.db";

        // Instanciar el DatabaseManager y crear las tablas
        DatabaseManager dbManager = new DatabaseManager(dbPath);
        dbManager.createTables();

        // Instanciar los DataManagers
        WeatherDataManager weatherDataManager = new WeatherDataManager(dbManager);
        ComparisonDataManager comparisonDataManager = new ComparisonDataManager(dbManager);
        DestinationsDataManager destinationsDataManager = new DestinationsDataManager(dbManager);

        // Configurar la conexión al broker de mensajes y los topics
        String brokerUrl = "tcp://localhost:61616";
        String weatherTopicName = "prediction.Weather";
        String comparisonTopicName = "comparison.Results";

        // Instanciar el EventSubscriber y suscribirse a los topics
        try {
            EventSubscriber eventSubscriber = new EventSubscriber(brokerUrl, "BusinessUnitClient", dbPath);
            eventSubscriber.subscribeToWeatherTopic(weatherTopicName, weatherDataManager::handleWeatherEvent);
            eventSubscriber.subscribeToComparisonTopic(comparisonTopicName, comparisonDataManager::handleComparisonEvent);

            System.out.println("Business Unit is now running and processing events.");

            // Actualizar los datos de la tabla Destinations
            destinationsDataManager.updateDestinationsData();
            System.out.println("Destinations data updated.");

        } catch (JMSException e) {
            e.printStackTrace();
            System.out.println("Failed to subscribe to topics due to an error: " + e.getMessage());
        }

        // Mantener el programa corriendo para que pueda continuar procesando eventos
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Business Unit interrupted, shutting down.");
                break;
            }
        }
    }
}
