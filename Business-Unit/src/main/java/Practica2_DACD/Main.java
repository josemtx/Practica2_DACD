package Practica2_DACD;

import Practica2_DACD.model.Book;
import Practica2_DACD.model.Location;

import javax.jms.JMSException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Configurar la ruta de la base de datos SQLite
        String dbPath = "datamart.db";

        // Instanciar el DatabaseManager y crear las tablas
        DatabaseManager dbManager = new DatabaseManager(dbPath);
        dbManager.createTables();

        // Crear una lista de objetos Book
        List<Book> books = new ArrayList<>();
        books.add(new Book("Paradisus", "g562818-d238899", LocalDate.parse("2024-01-15"), LocalDate.parse("2024-01-20"), new Location(27.99549, -15.41765)));
        books.add(new Book("Melia Palacio De Isora", "g1773834-d945835", LocalDate.parse("2024-01-15"), LocalDate.parse("2024-01-20"), new Location(28.463850790803008, -16.25097353346818)));
        books.add(new Book("La Palma  Princess", "g1175543-d638034", LocalDate.parse("2024-01-15"), LocalDate.parse("2024-01-20"), new Location(28.503323, -17.874559)));
        books.add(new Book("Hotel Fariones", "g662290-d282771", LocalDate.parse("2024-01-15"), LocalDate.parse("2024-01-20"), new Location(28.965080860301025, -13.556148106209083)));
        books.add(new Book("Evita Beach", "g1190272-d2645782", LocalDate.parse("2024-01-15"), LocalDate.parse("2024-01-20"), new Location(29.23141101200906, -13.503131221117982)));
        books.add(new Book("Parador de El Hierro", "g187474-d277394", LocalDate.parse("2024-01-15"), LocalDate.parse("2024-01-20"), new Location(27.809920552606453, -17.91474223115781)));
        books.add(new Book("Ibo Alfaro", "g674060-d670383", LocalDate.parse("2024-01-15"), LocalDate.parse("2024-01-20"), new Location(28.094369991798228, -17.109467831251514)));
        books.add(new Book("Barcelo Corralejo", "g580322-d678447", LocalDate.parse("2024-01-15"), LocalDate.parse("2024-01-20"), new Location(28.728468, -13.857411)));


        // Instanciar los DataManagers
        WeatherDataManager weatherDataManager = new WeatherDataManager(dbManager);
        ComparisonDataManager comparisonDataManager = new ComparisonDataManager(dbManager, books);
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

            TextInterfaceApp textInterfaceApp = new TextInterfaceApp(dbManager);
            textInterfaceApp.run();

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
