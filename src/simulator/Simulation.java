package simulator;


import javax.swing.*;
import java.io.File;

public class Simulation {

    private final SimulationParameters simulationParameters;


    public Simulation(SimulationParameters sp) {
        this.simulationParameters = sp;
    }

    public void start() {
        JFrame frame = new JFrame("Simulation Demo");
        frame.setSize(600,600);
        setPersonsId();
        Field field = new Field(simulationParameters.getPersons());
        BoardPanel panel = new BoardPanel(field, simulationParameters.getWidth(), simulationParameters.getHeight());
        frame.setContentPane(panel);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setPersonsId(){
        int id = 0;
        for(Person p : simulationParameters.getPersons()){
            p.setId(id++);
        }
    }
}
