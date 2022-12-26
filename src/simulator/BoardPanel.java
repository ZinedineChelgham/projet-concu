package simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.IntStream;
import java.util.List;



public class BoardPanel extends JPanel implements ActionListener {


    public final int GRID_WIDTH;
    public final int GRID_HEIGHT;
    public static int cellsize;
    private final List<Person> persons;

    public BoardPanel(List<Person> persons, int gridWidth, int gridHeight) {
        this.persons = persons;
        this.GRID_WIDTH = gridWidth;
        this.GRID_HEIGHT = gridHeight;
        cellsize = Math.max(Simulation.WINDOW_WIDTH /gridWidth, Simulation.WINDOW_HEIGHT /gridHeight)-15;
        new Timer(1200, this).start(); //TODO: remove
    }



    @Override
    protected  void paintComponent(Graphics g) {
        super.paintComponent(g);
        int xOffset = super.getWidth() / 2 - GRID_WIDTH / 2 * cellsize;
        int yOffset = super.getHeight() / 2 - GRID_HEIGHT / 2 * cellsize;
        g.translate(xOffset, yOffset);
        drawGrid(g);
        drawPersons(g);

    }

    private synchronized void drawPersons(Graphics g) {
        persons.stream().filter(p -> !p.isArrived()).forEach(p -> p.draw(g));
    }

    private void drawGrid(Graphics g) {
        // draw lines
        IntStream.rangeClosed(0, GRID_HEIGHT).forEach(i -> g.drawLine(0, i * cellsize, cellsize * GRID_WIDTH, i * cellsize));
        // draw columns
        IntStream.rangeClosed(0, GRID_WIDTH).forEach(i -> g.drawLine(i * cellsize, 0, i * cellsize, cellsize * GRID_HEIGHT));

        // draw two line in the middle to visualise the 4 grid separation
        g.setColor(Color.RED);
        ((Graphics2D) g).setStroke(new BasicStroke(3));
        g.drawLine(0, GRID_HEIGHT / 2 * cellsize, GRID_WIDTH * cellsize, GRID_HEIGHT / 2 * cellsize);
        g.drawLine(GRID_WIDTH / 2 * cellsize, 0, GRID_WIDTH / 2 * cellsize, GRID_HEIGHT * cellsize);
        //reset stroke
        ((Graphics2D) g).setStroke(new BasicStroke(1));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
