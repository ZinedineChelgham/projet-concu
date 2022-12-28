package simulator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class GridThread extends Thread {
    int id;
    final BlockingQueue<Person>[] queues;
    final List<Person> persons = new ArrayList<>();
    PositionVector startPos;
    int size;

    List<Person> totpersons;

    final Object lock = new Object();

    //constructor
    GridThread(int id, int x, int y, int size, BlockingQueue<Person>[] queues, List<Person> totpersons) {
        this.id = id;
        this.startPos = new PositionVector(x, y);
        this.size = size;
        this.queues = queues;
        this.totpersons = totpersons;
    }


    @Override
    public void run() {
        while (!totpersons.stream().allMatch(Person::isArrived)) {
            // Check if any person need to be retrieved from another thread
            queues[id].drainTo(persons);

            // Remove persons that have arrived or need to be transferred to another thread
            persons.removeIf(person -> person.isArrived() || !isPositionStillInGrid(person.curPos.x, person.curPos.y));

            // Update positions of remaining persons
            for (Person person : persons) {
                EMove move = person.getDecidedMove(this);
                if (move == null) continue;

                PositionVector nextPos = person.getNextPos(move);
                if (!isPositionStillInGrid(nextPos.x, nextPos.y)) { // we need to transfer the person to another thread
                    transferPerson(person, nextPos);
                    continue;
                }

                if (isPlaceEmpty(nextPos)) {
                    person.curPos = nextPos;
                } else {
                    Person otherPerson = getPersonOnPosition(nextPos);
                    assert otherPerson != null;
                    if (otherPerson.isArrived()) { // case when a person in this loop arrive to destination -> we need to dismiss it (impossible to modify the content while iterating on it)
                        person.curPos = nextPos;
                    } else if (person.id > otherPerson.id) { // older take the place and the younger reset
                        otherPerson.resetPosition();
                        person.curPos = nextPos;
                    }
                }
            }
        }
    }

    private void transferPerson(Person person, PositionVector nextPos) {
        int targetId = getTargetGridId(nextPos.x, nextPos.y);
        person.curPos = nextPos;
        person.startPos = nextPos.cloneVector(); // we need to update the start position as well cuz the person is now in a different thread the first one no longer handle this person
        queues[targetId].add(person);
    }


    public boolean isPositionStillInGrid(int x, int y) {
        return x >= startPos.x && x < getGridWidth() && y >= startPos.y && y < getGridHeight();
    }

    public boolean isPlaceEmpty(PositionVector pos) {
        return persons.stream().noneMatch(person -> person.curPos.equals(pos));
    }

    public boolean isPlaceEmpty(int x, int y) {
        return isPlaceEmpty(new PositionVector(x, y));
    }

    private Person getPersonOnPosition(PositionVector pos) {
        return persons.stream().filter(person -> person.curPos.equals(pos)).findFirst().orElse(null);
    }

    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * @return The quadrant of the grid that the point falls into.
     * 0 for top-left, 1 for top-right, 2 for bottom-left, 3 for bottom-right.
     */
    private int getTargetGridId(int x, int y) {
        int gridId = 0;
        if (x >= size) gridId += 1;
        if (y >= size) gridId += 2;
        return gridId;
    }

    private int getGridWidth() {
        return startPos.x + size;
    }

    private int getGridHeight() {
        return startPos.y + size;
    }
}
