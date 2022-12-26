package simulator;


import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.IntStream;

public class Simulation {

    private final SimulationParameters simulationParameters;
    static final int WINDOW_WIDTH = 800;
    static final int WINDOW_HEIGHT = 800;

    static final int NUM_THREADS = 4;

    GridThread[] threads = new GridThread[NUM_THREADS];

    public Simulation(SimulationParameters sp) {
        this.simulationParameters = sp;
    }

    public void start() throws InterruptedException {
        JFrame frame = new JFrame("Simulation Demo");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        initThreads();
        BoardPanel panel = new BoardPanel(simulationParameters.persons(), simulationParameters.width(), simulationParameters.height());
        frame.setContentPane(panel);frame.setResizable(true);frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        long startTime = System.nanoTime();
        Arrays.stream(threads).forEach(Thread::start);

        while(!simulationParameters.persons().stream().allMatch(Person::isArrived)) {}
        Arrays.stream(threads).forEach(Thread::stop);

        long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long actualMemUsed=afterUsedMem-beforeUsedMem;
        System.out.println("Memory used1: " + actualMemUsed);
        System.out.println("Mem 2: "  + new SystemMemory().getCurrentStats());
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;
        System.out.println("Execution time: " + duration + " ms");

        //close the frame after 3 seconds
        new Timer(3000, e -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING))).start();
    }

    private void initThreads(){
        //instanciate an array of BlockingQueue of Person
        @SuppressWarnings("unchecked")
        BlockingQueue<Person>[] queues = (BlockingQueue<Person>[]) new LinkedBlockingQueue[NUM_THREADS];
        IntStream.range(0, NUM_THREADS).forEach(i -> queues[i] = new LinkedBlockingQueue<>());

        int halfGridSize = simulationParameters.width()/2;
        threads[0] = new GridThread(0,0,0,halfGridSize, queues);
        threads[1] = new GridThread(1,halfGridSize,0,halfGridSize, queues);
        threads[2] = new GridThread(2,0,halfGridSize,halfGridSize, queues);
        threads[3] = new GridThread(3,halfGridSize,halfGridSize,halfGridSize, queues);
        populateThreads();
    }

    private void populateThreads(){
        for(Person p : simulationParameters.persons()){
            for(GridThread t : threads){
                if(t.isPositionStillInGrid(p.curPos.x, p.curPos.y)){
                    t.addPerson(p);
                    break;
                }
            }
        }
    }
}
