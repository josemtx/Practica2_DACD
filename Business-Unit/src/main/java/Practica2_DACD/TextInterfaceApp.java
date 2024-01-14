package Practica2_DACD;

import java.util.Scanner;

public class TextInterfaceApp {

    private final DatabaseManager dbManager;

    public TextInterfaceApp(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("datamart.db");

        // Solicitar el nombre del hotel al usuario
        System.out.print("Ingrese el nombre del hotel para ver la información relevante: ");
        String hotelName = scanner.nextLine();

        // Obtener y mostrar la información relevante
        showHotelInformation(hotelName);

        scanner.close();
    }

    private void showHotelInformation(String hotelName) {
        // Lógica para obtener la información del hotel de la base de datos
        DestinationInfo destinationInfo = dbManager.getDestinationInfo(hotelName);
        if (destinationInfo != null) {
            // Suponiendo que DestinationInfo tiene métodos getters para obtener información
            System.out.println("Información del Hotel: " + destinationInfo.getHotelName());
            System.out.println("Tasa media: " + destinationInfo.getAverageRate());
            System.out.println("Moneda: " + destinationInfo.getCurrency());
            System.out.println("Temperatura media: " + destinationInfo.getAverageTemperature());
            System.out.println("Probabilidad de precipitación: " + destinationInfo.getProbabilityOfPrecipitation());
        } else {
            System.out.println("Información no disponible para el hotel especificado.");
        }
    }
}

