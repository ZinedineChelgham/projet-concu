package simulator;

public class Main {

    public static void main(String... args) {
        SimulationParameters sp = new SimulationParser().generatePersons(20,4,4);
        new Simulation(sp).start();

    }
}
