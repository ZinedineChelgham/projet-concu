package simulator;

public class Main {

    public static void main(String... args) {
        SimulationParameters sp = new SimulationParser().generatePersons(50,10,10);
        new Simulation(sp).start();

    }
}
