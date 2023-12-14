package Practica2_DACD;

public class Main {
    public static void main(String[] args) {
        String brokerUrl = "tcp://localhost:61616"; // Replace with your ActiveMQ broker URLl
        String topic = "prediction.Weather";
        FileCreator builder = new FileCreator(brokerUrl, topic);
        builder.start();
    }
}