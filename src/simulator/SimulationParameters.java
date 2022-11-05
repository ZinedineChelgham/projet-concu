package simulator;

import java.util.List;

public record SimulationParameters(int width, int height, List<Person> persons) {

    public  int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Person> getPersons() {
        return persons;
    }
}
