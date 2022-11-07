package simulator;


import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private final SimulationParameters simulationParameters;
    static final int WIDTH = 800;
    static final int HEIGHT = 800;

    public Simulation(SimulationParameters sp) {
        this.simulationParameters = sp;
    }

    public void start() {
        JFrame frame = new JFrame("Simulation Demo");
        frame.setSize(WIDTH,HEIGHT);
        setPersonsId();
        Field field = new Field(simulationParameters.getPersons(), simulationParameters.getWidth(), simulationParameters.getHeight());
        BoardPanel panel = new BoardPanel(field, simulationParameters.getWidth(), simulationParameters.getHeight());
        frame.setContentPane(panel);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startThreads();
    }

    private void startThreads(){
        List<Thread> tPersons = new ArrayList<>();
        for(Person p : simulationParameters.getPersons()){
            tPersons.add(new Thread(p));
        }
        for (Thread thread : tPersons){
            thread.start();
        }
    }

    private void setPersonsId(){
        int id = 0;
        for(Person p : simulationParameters.getPersons()){
            p.setId(id++);
        }
    }
}
