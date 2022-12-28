package simulator;


import javax.swing.*;
import java.awt.event.WindowEvent;
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
        frame.setVisible(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long startTime = System.nanoTime();
        startThreads();
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

    private void startThreads(){
        List<Thread> tPersons = new ArrayList<>();
        for(Person p : simulationParameters.getPersons()){
            tPersons.add(new Thread(p));
        }
        for (Thread thread : tPersons){
            thread.start();
        }

        for(Thread thread : tPersons){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void setPersonsId(){
        int id = 0;
        for(Person p : simulationParameters.getPersons()){
            p.setId(id++);
        }
    }
}
