package simulator;


import javax.swing.*;
import java.awt.event.WindowEvent;
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
        frame.setVisible(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long startTime = System.nanoTime();

        while(!simulationParameters.persons().stream().allMatch(Person::isArrived)) {
            for(Person p : simulationParameters.getPersons()){
                if(!p.isArrived()) p.move();
                field.checkTheArrived();
            }
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;
        System.out.println("Execution time: " + duration + " ms");
        long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long actualMemUsed=afterUsedMem-beforeUsedMem;
        System.out.println("Memory used1: " + actualMemUsed);
        System.out.println("Mem 2: "  + new SystemMemory().getCurrentStats());
        //close the frame after 3 seconds
        new Timer(100, e -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING))).start();



    }

    private void setPersonsId(){
        int id = 0;
        for(Person p : simulationParameters.getPersons()){
            p.setId(id++);
        }
    }
}
