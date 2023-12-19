package Practica2_DACD;

public class Main {
    public static void main(String[] args) {
        String brokerUrl = "tcp://localhost:61616";
        FileCreator builder = new FileCreator(brokerUrl);
        builder.startListeningToTopics("prediction.Weather", "comparison.Results");
    }
}