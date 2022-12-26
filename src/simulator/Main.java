package simulator;

import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String... args) throws InterruptedException {
        SimulationParameters sp = new SimulationParser().generatePersons(16, 4, 4);
        new Simulation(sp).start();
    }


}
