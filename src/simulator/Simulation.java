package simulator;


import javax.swing.*;

public class Simulation {

    // need greed




    public void start() {
        JFrame frame = new JFrame("Simulation Demo");
        frame.setSize(600,600);
        frame.setContentPane(new BoardPanel());
        frame.setResizable(false);
        frame.setVisible(true);

    }
}
