package simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class SimulationParser {
    public SimulationParameters parse(String filename) {
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            String nextLine = myReader.nextLine();
            String[] grid_sizes = nextLine.split(" ");
            int width = Integer.parseInt(grid_sizes[0]);
            int height = Integer.parseInt(grid_sizes[1]);
            ArrayList<Person> personsList = new ArrayList();
            while (myReader.hasNextLine()) {
                String nextPerson = myReader.nextLine();
                String[] nextPersonArray = nextPerson.split(" ");
                int nextX = Integer.parseInt(nextPersonArray[0]);
                int nextY = Integer.parseInt(nextPersonArray[1]);
                int nextGoalX = Integer.parseInt(nextPersonArray[2]);
                int nextGoalY = Integer.parseInt(nextPersonArray[3]);
                personsList.add(new Person(nextX, nextY, 1, 1, nextGoalX, nextGoalY));
            }
            myReader.close();
            return new SimulationParameters(width, height, personsList);
        } catch (
                FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return new SimulationParameters(10,10,new ArrayList<>());
    }

}
