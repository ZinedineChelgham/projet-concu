package simulator;

public class Main {

    public static void main(String... args) {
        new Simulation().start();
        SimulationParser simulationParser = new SimulationParser();
        SimulationParameters simulationParameters = simulationParser.parse("test.txt");
        System.out.println(simulationParameters.getWidth());
        System.out.println(simulationParameters.getHeight());
        System.out.println(simulationParameters.getPersons());
    }
}
