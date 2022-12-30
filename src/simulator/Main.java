package simulator;

import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String... args) throws InterruptedException {
        SimulationParameters sp = new SimulationParser().generatePersons(36, 6, 6);
        new Simulation(sp).start();
    }


}
