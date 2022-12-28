package simulator;

public class Main {

    public static void main(String... args) {
        SimulationParameters sp = new SimulationParser().generatePersons(1000,100,100);
        new Simulation(sp).start();

    }
}
