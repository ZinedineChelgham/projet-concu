package simulator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static simulator.BoardPanel.cellsize;

public class Person  {

    PositionVector startPos;
    PositionVector curPos;
    PositionVector finalPos;
    int id;
    final Color color;

    public Person(int x, int y, int xGoal, int yGoal, Color color) {
        this.startPos = new PositionVector(x, y);
        this.curPos = startPos.cloneVector();
        this.finalPos = new PositionVector(xGoal, yGoal);
        this.color = color;
    }

    //constructor witth id field
    public Person(int id, int x, int y, int xGoal, int yGoal, Color color) {
        this.id = id;
        this.startPos = new PositionVector(x, y);
        this.curPos = startPos.cloneVector();
        this.finalPos = new PositionVector(xGoal, yGoal);
        this.color = color;
    }

    public void setId(int id) {
        this.id = id;
    }


    public EMove getDecidedMove(GridThread gridThread) {
        List<EMove> bestMoves = getBestMoves();
        if (bestMoves.isEmpty()) return null;
        List<EMove> movesOnEmptyPlace = getMovesOnEmptyPlace(bestMoves, gridThread);
        return movesOnEmptyPlace.isEmpty() ? bestMoves.get(0) : movesOnEmptyPlace.get(0);
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

    public PositionVector getNextPos(EMove move) {
        return new PositionVector(getNextXPos(move), getNextYPos(move));
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

    public List<EMove> getMovesOnEmptyPlace(List<EMove> moves, GridThread gridThread) {
        List<EMove> costLessMoves = new ArrayList<>();
        for (EMove move : moves) {
            switch (move) {
                case LEFT -> {
                    if (gridThread.isPlaceEmpty(curPos.x - 1, curPos.y)) costLessMoves.add(move);
                }
                case RIGHT -> {
                    if (gridThread.isPlaceEmpty(curPos.x + 1, curPos.y)) costLessMoves.add(move);
                }
                case UP -> {
                    if (gridThread.isPlaceEmpty(curPos.x, curPos.y + 1)) costLessMoves.add(move);
                }
                case DOWN -> {
                    if (gridThread.isPlaceEmpty(curPos.x, curPos.y - 1)) costLessMoves.add(move);
                }
            }
        }
        return costLessMoves;
    }


    public void draw(Graphics g) {
        int len =  cellsize / 2;
        if (color != null) g.setColor(color);
        int posX = (cellsize / 2 - len / 2) + curPos.x * cellsize;
        int posY = (cellsize / 2 - len / 2) + curPos.y * cellsize;
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
                ", startPos=" + startPos +
                ", curPos=" + curPos +
                ", finalPos=" + finalPos +
                ", color=" + color +
                '}';
    }
}
