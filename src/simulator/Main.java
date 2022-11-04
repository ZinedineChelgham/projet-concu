package simulator;

public class Main {

    public static void main(String... args) {
        SimulationParameters sp = new SimulationParser().parse("test.txt");
        new Simulation(sp).start();

    }
}
