package simulator;

import java.util.ArrayList;
import java.util.List;

public class SimulationParameters {
    private int width;
    private int height;
    private List<Person> persons;

    public SimulationParameters(int width, int height, List<Person> persons) {
        this.width = width;
        this.height = height;
        this.persons = persons;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Person> getPersons() {
        return persons;
    }
}
