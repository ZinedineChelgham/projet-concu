package simulator;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SimulationParser {


    private final List<Color> givenColors = new ArrayList<>();

    public SimulationParameters parse(String filename) {
        givenColors.add(Color.BLACK); // we exclude black and white so that the drawn id is always visible
        givenColors.add(Color.WHITE);
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            String nextLine = myReader.nextLine();
            String[] grid_sizes = nextLine.split(" ");
            int width = Integer.parseInt(grid_sizes[0]);
            int height = Integer.parseInt(grid_sizes[1]);
            ArrayList personsList = new ArrayList();
            while (myReader.hasNextLine()) {
                String nextPerson = myReader.nextLine();
                String[] nextPersonArray = nextPerson.split(" ");
                // check if nextPersonArray is valid if not continue to next iteration
                if (nextPersonArray.length != 4) continue;
                int nextX = Integer.parseInt(nextPersonArray[0]);
                int nextY = Integer.parseInt(nextPersonArray[1]);
                int nextGoalX = Integer.parseInt(nextPersonArray[2]);
                int nextGoalY = Integer.parseInt(nextPersonArray[3]);
                personsList.add(new Person(nextX, nextY, nextGoalX, nextGoalY, generateNewColor()));
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

    public SimulationParameters generatePersons(int nbPersons, int width, int height) {
        givenColors.add(Color.BLACK); // we exclude black so that the drawn id is always visible
        givenColors.add(Color.WHITE);
        ArrayList personsList = new ArrayList();
        Random random = new Random();
        for(int i = 0; i < nbPersons; i++){
            int nextX = random.nextInt(width);
            int nextY = random.nextInt(height);
            //if x and y already exist skip this iteration
            if(personsList.stream().anyMatch(p -> ((Person)p).startPos.x == nextX && ((Person)p).startPos.y == nextY)) continue;
            int nextGoalX = random.nextInt(width);
            int nextGoalY = random.nextInt(height);
            personsList.add(new Person(nextX, nextY, nextGoalX, nextGoalY, generateNewColor()));
        }
        return new SimulationParameters(width, height, personsList);
    }

    private Color generateNewColor(){
        Color c;
        do{
            c = new Color((int)(Math.random() * 0x1000000));
        }while(givenColors.contains(c));
        givenColors.add(c);
        return c;
    }

}
