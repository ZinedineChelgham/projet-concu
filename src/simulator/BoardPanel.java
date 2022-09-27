package simulation;

import javax.swing.*;
import java.awt.*;



public class BoardPanel extends JPanel  {

    private final int XOFFSET = 100;
    private final int YOFFSET = 50;
    private final int CELLSIZE = 35;

    public BoardPanel() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw lines
        for(int i=0; i<=10; i++){
            g.drawLine(XOFFSET, YOFFSET + i*CELLSIZE,  350 + XOFFSET, YOFFSET + i*CELLSIZE);
        }

        //draw columns
        for(int i=0; i<=10; i++){
            g.drawLine(XOFFSET + i*CELLSIZE, YOFFSET, XOFFSET + i*CELLSIZE, 350 + YOFFSET);
        }

    }
}
