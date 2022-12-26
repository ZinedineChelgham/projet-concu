package simulator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static simulator.BoardPanel.CELLSIZE;


public class Person extends Rectangle implements Runnable {

    final int xStart, yStart;
    final int xGoal, yGoal;
    private int id;
    private Field sharedField;
    private Color color = null;


    public Person(int id, int x, int y, int xGoal, int yGoal, Color color) {
        super(x, y, 15, 15);
        this.id = id;
        this.xStart = x;
        this.yStart = y;
        this.xGoal = xGoal;
        this.yGoal = yGoal;
        this.color = color;

    }

    public Person(int x, int y, int xGoal, int yGoal, Color color) {
        super(x, y, 15, 15);
        this.id = -1;
        this.xStart = x;
        this.yStart = y;
        this.xGoal = xGoal;
        this.yGoal = yGoal;
        this.color = color;
    }

    @Override
    public void run() {

    }

    public void setId(int id){
        this.id = id;
    }


    public void move() {
        List<EMove> bestMoves = getBestMoves();
        if (bestMoves.isEmpty()) return;
        List<EMove> movesOnEmptyPlace = getMovesOnEmptyPlace(bestMoves);
        if (movesOnEmptyPlace.isEmpty()) {
            if (id > Objects.requireNonNull(sharedField.getPersonAt(getNextXPos(bestMoves.get(0)), getNextYPos(bestMoves.get(0)))).id) {
                // Im the oldest
                EMove move = bestMoves.get(0);
                sharedField.resetPersonAt(getNextXPos(move), getNextYPos(move));
                moveAccordingTo(move);
            }
        } else {
            moveAccordingTo(movesOnEmptyPlace.get(0));
        }

    }

    public void setPosOutside() {
        x = y = Integer.MAX_VALUE;
    }

    public int getNextYPos(EMove move) {
        switch (move) {
            case UP -> {
                return y + 1;
            }
            case DOWN -> {
                return y - 1;
            }
        }
        return y;
    }

    public int getNextXPos(EMove move) {
        switch (move) {
            case LEFT -> {
                return x - 1;
            }
            case RIGHT -> {
                return x + 1;
            }
        }
        return x;
    }

    public void moveAccordingTo(EMove move) {
        switch (move) {
            case LEFT -> x--;
            case RIGHT -> x++;
            case UP -> y++;
            case DOWN -> y--;
        }
    }

    private List<EMove> getBestMoves() {
        List<EMove> bestMoves = new ArrayList<>();
        if (x == xGoal && y == yGoal) return bestMoves;
        if (x < xGoal) bestMoves.add(EMove.RIGHT);
        if (x > xGoal) bestMoves.add(EMove.LEFT);
        if (y < yGoal) bestMoves.add(EMove.UP);
        if (y > yGoal) bestMoves.add(EMove.DOWN);
        return bestMoves;
    }

    public List<EMove> getMovesOnEmptyPlace(List<EMove> moves) {
        List<EMove> costLessMoves = new ArrayList<>();
        for (EMove move : moves) {
            switch (move) {
                case LEFT -> {
                    if (sharedField.isPlaceFree(x - 1, y)) costLessMoves.add(move);
                }
                case RIGHT -> {
                    if (sharedField.isPlaceFree(x + 1, y)) costLessMoves.add(move);
                }
                case UP -> {
                    if (sharedField.isPlaceFree(x, y + 1)) costLessMoves.add(move);
                }
                case DOWN -> {
                    if (sharedField.isPlaceFree(x, y - 1)) costLessMoves.add(move);
                }
            }
        }
        return costLessMoves;
    }

    public void setSharedField(Field field) {
        this.sharedField = field;
    }

    public void draw(Graphics g) {
        if (color != null) g.setColor(color);
        int posX = (CELLSIZE / 2 - width / 2) + x * CELLSIZE;
        int posY = (CELLSIZE / 2 - height / 2) + y * CELLSIZE;
        g.fillRect(posX, posY, width, height);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier New", Font.BOLD, 10));
        int idX = (CELLSIZE / 2 - width / 5) + x * CELLSIZE;
        int idY = (CELLSIZE / 2 + width / 4) + y * CELLSIZE;
        g.drawString(String.valueOf(id), idX, idY);
    }

    public void resetPosition() {
        x = xStart;
        y = yStart;
    }

    public boolean isArrived() {
        return x == xGoal && y == yGoal || x == Integer.MAX_VALUE && y == Integer.MAX_VALUE;
    }

    public void setColor(Color color) {
        this.color = color;
    }


    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", xStart=" + xStart +
                ", yStart=" + yStart +
                ", xGoal=" + xGoal +
                ", yGoal=" + yGoal +
                ", sharedField=" + sharedField +
                ", color=" + color +
                ", x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
