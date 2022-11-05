package simulator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static simulator.BoardPanel.CELLSIZE;


public class Person implements Runnable {

    private final int len;
    PositionVector startPos;
    PositionVector curPos;
    PositionVector finalPos;
    protected int id;
    private Field sharedField;
    private final Color color;

    public Person(int x, int y, int xGoal, int yGoal, Color color) {
        this.len = CELLSIZE / 2;
        this.startPos = new PositionVector(x, y);
        this.curPos = startPos.cloneVector();
        this.finalPos = new PositionVector(xGoal, yGoal);
        this.color = color;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!isArrived()) {
            try {
                move();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void move() throws InterruptedException {
        List<EMove> bestMoves = getBestMoves();
        if (bestMoves.isEmpty()) return;
        List<EMove> movesOnEmptyPlace = getMovesOnEmptyPlace(bestMoves);
        EMove finalMove = movesOnEmptyPlace.isEmpty() ? bestMoves.get(0) : movesOnEmptyPlace.get(0);
        sharedField.placePerson(this, new PositionVector(getNextXPos(finalMove), getNextYPos(finalMove)));
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
                    if (sharedField.isPlaceFree(curPos.x + 1, curPos.y)) costLessMoves.add(move);
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
        g.setFont(new Font("Courier New", Font.BOLD, len / 2));
        // center the id in the cells
        int idX = posX + (len - g.getFontMetrics().stringWidth(String.valueOf(id))) / 2;
        int idY = posY + (len - g.getFontMetrics().getHeight()) / 2 + g.getFontMetrics().getAscent();
        g.drawString(String.valueOf(id), idX, idY);
    }

    public void resetPosition() {
        curPos = startPos.cloneVector();
    }

    public boolean isArrived() {
        return curPos.equals(finalPos);
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
