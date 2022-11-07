package simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BoardPanel extends JPanel implements ActionListener {


    public final int GRID_WIDTH;
    public final int GRID_HEIGHT;
    private final Field field;

    public BoardPanel(Field field, int gridWidth, int gridHeight) {
        this.field = field;
        this.GRID_WIDTH = gridWidth;
        this.GRID_HEIGHT = gridHeight;
        new Timer(1000, this).start();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellsize = field.getCellsize();
        int xOffset = super.getWidth() / 2 - GRID_WIDTH / 2 * cellsize;
        int yOffset = super.getHeight() / 2 - GRID_HEIGHT / 2 * cellsize;
        g.translate(xOffset, yOffset);
        drawGrid(g);
        field.drawPersons(g);

    }

    private void drawGrid(Graphics g) {
        int cellsize = field.getCellsize();
        for (int i = 0; i <= GRID_HEIGHT; i++) {
            // draw lines
            g.drawLine(0, i * cellsize, cellsize * GRID_WIDTH, i * cellsize);
        }
        for (int i = 0; i <= GRID_WIDTH; i++) {
            // draw columns
            g.drawLine(i * cellsize, 0, i * cellsize, cellsize * GRID_HEIGHT);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        field.checkTheArrived();
        repaint();
    }

    public int getGRID_WIDTH() {
        return GRID_WIDTH;
    }

    public int getGRID_HEIGHT() {
        return GRID_HEIGHT;
    }

}
