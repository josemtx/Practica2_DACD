package Practica2_DACD.control;

import Practica2_DACD.model.Book;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ComparationController {

    public void runComparationProvider() {
        // Crear una lista de objetos Book con fechas definidas
        List<Book> books = Arrays.asList(
                new Book("g188590-d232321", LocalDate.of(2024, 1, 25), LocalDate.of(2024, 1, 26)),
                new Book("g188590-d12967879", LocalDate.of(2024, 1, 25), LocalDate.of(2024, 1, 30))
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
