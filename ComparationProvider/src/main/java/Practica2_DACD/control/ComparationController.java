package Practica2_DACD.control;

import Practica2_DACD.model.Book;
import Practica2_DACD.model.Location;

import javax.jms.JMSException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ComparationController {

    public void runComparationProvider() throws JMSException {
        // Crear una lista de objetos Book con fechas definidas
        List<Book> books = Arrays.asList(
                new Book("Paradisus", "g562818-d238899", LocalDate.parse("2024-01-15"), LocalDate.parse("2024-01-20"), new Location(27.99549, -15.41765)),
                new Book("Melia Palacio De Isora", "g1773834-d945835", LocalDate.parse("2024-01-15"), LocalDate.parse("2024-01-20"), new Location(28.463850790803008, -16.25097353346818)),
                new Book("La Palma  Princess", "g1175543-d638034", LocalDate.parse("2024-01-15"), LocalDate.parse("2024-01-20"), new Location(28.503323, -17.874559)),
                new Book("Hotel Fariones", "g662290-d282771", LocalDate.parse("2024-01-15"), LocalDate.parse("2024-01-20"), new Location(28.965080860301025, -13.556148106209083)),
                new Book("Evita Beach", "g1190272-d2645782", LocalDate.parse("2024-01-15"), LocalDate.parse("2024-01-20"), new Location(29.23141101200906, -13.503131221117982)),
                new Book("Parador de El Hierro", "g187474-d277394", LocalDate.parse("2024-01-15"), LocalDate.parse("2024-01-20"), new Location(27.809920552606453, -17.91474223115781)),
                new Book("Ibo Alfaro", "g674060-d670383", LocalDate.parse("2024-01-15"), LocalDate.parse("2024-01-20"), new Location(28.094369991798228, -17.109467831251514)),
                new Book("Barcelo Corralejo", "g580322-d678447", LocalDate.parse("2024-01-15"), LocalDate.parse("2024-01-20"), new Location(28.728468, -13.857411))

                // Agregar más objetos Book según sea necesario
        );

        // Crear una instancia de ComparationProvider con la lista de libros y la URL del broker
        ComparationProvider comparationProvider = new ComparationProvider(books, "tcp://localhost:61616");

        try {
            // Invocar el método para recuperar y enviar datos al topic 'comparison.Results'
            comparationProvider.fetchAndSendComparationData();
            System.out.println("Datos enviados al topic 'comparison.Results' con éxito.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Hubo un error al intentar enviar los datos al topic 'comparison.Results'.");
        }
    }
}
