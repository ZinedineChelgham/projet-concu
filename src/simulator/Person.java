package simulator;

import java.awt.*;

/**
 * @author : Zinedine Chelgham
 **/

public class Person extends Rectangle implements Runnable {

    private final int xGoal;
    private final int yGoal;


    public Person(int x, int y, int width, int height, int xGoal, int yGoal) {
        super(x, y, width, height);
        this.xGoal = xGoal;
        this.yGoal = yGoal;
    }

    @Override
    public void run() {

    }
}
