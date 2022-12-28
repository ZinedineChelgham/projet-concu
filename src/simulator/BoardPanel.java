package simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BoardPanel extends JPanel implements ActionListener {


    static final int CELLSIZE = 35;
    private final int GRID_WIDTH;
    private final int GRID_HEIGHT;
    private final Field field;

    public BoardPanel(Field field, int gridWidth, int gridHeight) {
        this.field = field;
        this.GRID_WIDTH = gridWidth;
        this.GRID_HEIGHT = gridHeight;
       // new Timer(10, this).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int xOffset = super.getWidth() / 2 - GRID_WIDTH / 2 * CELLSIZE;
        int yOffset = super.getHeight() / 2 - GRID_HEIGHT / 2 * CELLSIZE;
        g.translate(xOffset, yOffset);
        drawGrid(g);
        drawPersons(g);

    }

    private void makePersonsMove() {
        for (Person p : field.getPersons()) {
            if (!p.isArrived()) p.move();
        }
    }

    private void drawPersons(Graphics g) {
        for (Person p : field.getPersons()) {
            if(p.x > GRID_WIDTH) continue; //person is out
            p.draw(g);
        }
    }

    private void drawGrid(Graphics g) {
        for (int i = 0; i <= GRID_HEIGHT; i++) {
            // draw lines
            g.drawLine(0, i * CELLSIZE, CELLSIZE * GRID_WIDTH, i * CELLSIZE);
        }
        for (int i = 0; i <= GRID_WIDTH; i++) {
            // draw columns
            g.drawLine(i * CELLSIZE, 0, i * CELLSIZE, CELLSIZE * GRID_HEIGHT);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       // field.checkTheArrived();
       // makePersonsMove();
        repaint();
    }
}
