package simulator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static simulator.BoardPanel.CELLSIZE;


public class Person implements Runnable {

    private int id;
    private final int len;
    PositionVector startPos;
    PositionVector curPos;
    PositionVector finalPos;
    private Field sharedField;
    private Color color;


    public Person(int id, int x, int y, int xGoal, int yGoal, Color color) {
        this.id = id;
        this.len = 15;
        this.startPos = new PositionVector(x, y);
        this.curPos = startPos;
        this.finalPos = new PositionVector(xGoal, yGoal);
        this.color = color;

    }

    public Person(int x, int y, int xGoal, int yGoal, Color color) {
        this.len = 15;
        this.startPos = new PositionVector(x, y);
        this.curPos = startPos.myClone();
        this.finalPos = new PositionVector(xGoal, yGoal);
        this.color = color;
    }

    public void setId(int id){
        this.id = id;
    }

    @Override
    public void run() {

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
        curPos.x = Integer.MAX_VALUE;
        curPos.y = Integer.MAX_VALUE;
    }

    public int getNextYPos(EMove move) {
        switch (move) {
            case UP -> {
                return curPos.y + 1;
            }
            case DOWN -> {
                return curPos.y - 1;
            }
        }
        return curPos.y;
    }

    public int getNextXPos(EMove move) {
        switch (move) {
            case LEFT -> {
                return curPos.x - 1;
            }
            case RIGHT -> {
                return curPos.x + 1;
            }
        }
        return curPos.x;
    }

    public void moveAccordingTo(EMove move) {
        switch (move) {
            case LEFT -> curPos.add(-1,0);
            case RIGHT -> curPos.add(1,0);
            case UP -> curPos.add(0,1);
            case DOWN -> curPos.add(0,-1);
        }
    }

    private List<EMove> getBestMoves() {
        List<EMove> bestMoves = new ArrayList<>();
        if (curPos.equals(finalPos)) return bestMoves;
        if (curPos.x < finalPos.x) bestMoves.add(EMove.RIGHT);
        if (curPos.x > finalPos.x) bestMoves.add(EMove.LEFT);
        if (curPos.y < finalPos.y) bestMoves.add(EMove.UP);
        if (curPos.y > finalPos.y) bestMoves.add(EMove.DOWN);
        return bestMoves;
    }

    public List<EMove> getMovesOnEmptyPlace(List<EMove> moves) {
        List<EMove> costLessMoves = new ArrayList<>();
        for (EMove move : moves) {
            switch (move) {
                case LEFT:
                    if (sharedField.isPlaceFree(curPos.x - 1, curPos.y)) costLessMoves.add(move);
                    break;
                case RIGHT:
                    if (sharedField.isPlaceFree(curPos.x + 1 , curPos.y)) costLessMoves.add(move);
                    break;
                case UP:
                    if (sharedField.isPlaceFree(curPos.x, curPos.y + 1)) costLessMoves.add(move);
                    break;
                case DOWN:
                    if (sharedField.isPlaceFree(curPos.x, curPos.y - 1)) costLessMoves.add(move);
                    break;
            }
        }
        return costLessMoves;
    }

    public void setSharedField(Field field) {
        this.sharedField = field;
    }

    public void draw(Graphics g) {
        if (color != null) g.setColor(color);
        int posX = (CELLSIZE / 2 - len / 2) + curPos.x * CELLSIZE;
        int posY = (CELLSIZE / 2 - len / 2) + curPos.y * CELLSIZE;
        g.fillRect(posX, posY, len, len);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier New", Font.BOLD, 10));
        int idX = (CELLSIZE / 2 - len / 5) + posX * CELLSIZE;
        int idY = (CELLSIZE / 2 + len / 4) + posY * CELLSIZE;
        g.drawString(String.valueOf(id), idX, idY);
    }

    public void resetPosition() {
        curPos = startPos.myClone();
    }

    public boolean isArrived() {
        return curPos.equals(finalPos);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", len=" + len +
                ", startPos=" + startPos +
                ", curPos=" + curPos +
                ", finalPos=" + finalPos +
                ", sharedField=" + sharedField +
                ", color=" + color +
                '}';
    }
}
