package Practica2_DACD.control;

import Practica2_DACD.model.Book;

import javax.jms.JMSException;
import java.io.IOException;
import java.util.List;

public class ComparationProvider {

    private final List<Book> bookList;
    private final ActiveMQSender activeMQSender;

    public ComparationProvider(List<Book> bookList, String brokerUrl) throws JMSException {
        this.bookList = bookList;
        this.activeMQSender = new ActiveMQSender(brokerUrl);
    }

    public void fetchAndSendComparationData() throws IOException {
        for (Book book : bookList) {
            // Obtener la respuesta directamente de la API
            String response = new Response().sendGetRequest(book.getHotel_Key(), book.getCheckIn(), book.getCheckOut());
            System.out.println("Response from API: " + response);

            // Enviar la respuesta completa al topic 'comparison.Results'
            activeMQSender.sendMessage("comparison.Results", response);
        }
        System.out.println("Datos enviados al topic 'comparison.Results' con éxito.");
    }
}
