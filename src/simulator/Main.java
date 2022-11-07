package simulator;

public class Main {

    public static void main(String... args) {
        SimulationParameters sp = new SimulationParser().parse("initialisation_file.txt");
        new Simulation(sp).start();

    }
}
