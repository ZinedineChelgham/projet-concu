package simulator;

import java.awt.*;
import java.util.List;
import java.util.Objects;

import static simulator.Simulation.WIDTH;
import static simulator.Simulation.HEIGHT;


public class Field {

    private final Person[][] sharedField;
    private final int cellsize;
    List<Person> persons;

    public Field(List<Person> persons, int width, int height) {
        this.cellsize = Math.max(WIDTH/width,HEIGHT/height);
        this.persons = persons;
        sharedField = new Person[height][width];
        for (Person person : persons) this.sharedField[person.curPos.y][person.curPos.x] = person;
        setFieldForPersons();

    }

    public int getCellsize() {
        return cellsize;
    }


    public synchronized void placePerson(Person p, PositionVector desiredPos) {
        if (!isPlaceFree(desiredPos.x, desiredPos.y)) {
            Person p2 = Objects.requireNonNull(getPersonAt(desiredPos));
            if (p.id > p2.id) {
                resetPosition(p2);
                updatePersonPosition(p, desiredPos);
            }
        }else updatePersonPosition(p, desiredPos);
        checkTheArrived();
    }

    private void resetPosition(Person p) {
        sharedField[p.curPos.y][p.curPos.x] = null;
        p.curPos = p.startPos.cloneVector();
        sharedField[p.curPos.y][p.curPos.x] = p;
    }

    private void updatePersonPosition(Person p, PositionVector desiredPos) {
        sharedField[p.curPos.y][p.curPos.x] = null;
        sharedField[desiredPos.y][desiredPos.x] = p;
        p.curPos = desiredPos.cloneVector();
    }


    private void setFieldForPersons() {
        for (Person p : persons) p.setSharedField(this);
    }


    public boolean isPlaceFree(int x, int y) {
        //check if the place is outside the field
        if (x < 0 || x >= sharedField[0].length || y < 0 || y >= sharedField.length) return false;
        return sharedField[y][x] == null;
    }

    public Person getPersonAt(PositionVector pos) {
        return sharedField[pos.y][pos.x];
    }

    public void checkTheArrived() {
        for (Person p : persons) if (p.isArrived()) removeFromField(p);
    }

    private  void removeFromField(Person p) {
        sharedField[p.curPos.y][p.curPos.x] = null;
    }

    public void drawPersons(Graphics g) {
        for (Person p : persons) {
            if(p.isArrived()) continue; //person is out
            p.draw(g);
        }
    }

}
