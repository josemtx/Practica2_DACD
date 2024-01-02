package Practica2_DACD.control;

import javax.jms.JMSException;

public class Main {

    public static void main(String[] args) throws JMSException {
        ComparationController controller = new ComparationController();
        controller.runComparationProvider();
    }
}
