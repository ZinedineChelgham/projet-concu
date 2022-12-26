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



    //constructor
    GridThread(int id, int x, int y, int size, BlockingQueue<Person>[] queues){
        this.id = id;
        this.startPos = new PositionVector(x, y);
        this.size = size;
        this.queues = queues;
    }



    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1500); //TODO: remove
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // Check if any people need to be retrieved from another thread
            Person p = queues[id].poll();
            while (p != null) {
                persons.add(p);
                p = queues[id].poll();
            }

            // Remove persons that have arrived or need to be transferred to another thread
            persons.removeIf(person -> person.isArrived() || !isPositionStillInGrid(person.curPos.x, person.curPos.y));

            // Update positions of remaining persons
            for (Person person : persons) {
                EMove move = person.getDecidedMove(this);
                assert move != null;

                PositionVector nextPos = person.getNextPos(move);
                if(!isPositionStillInGrid(person.getNextXPos(move), person.getNextYPos(move))){ // we need to transfer the person to another thread
                    int targetId = getTargetGridId(person.getNextXPos(move), person.getNextYPos(move));
                    queues[targetId].add(person);
                    person.curPos = nextPos;
                    continue;
                }

                if (isPlaceEmpty(nextPos)) {
                    person.curPos = nextPos;
                } else {
                    Person otherPerson = getPersonOnPosition(nextPos);
                    assert otherPerson != null;
                    if(otherPerson.isArrived()) { // case when a person in this loop arrive to destination -> we need to dismiss it (impossible to modify the content while iterating on it
                        person.curPos = nextPos;
                        continue;
                    }
                    if (person.id > otherPerson.id) { // older take the place and the younger reset
                        otherPerson.resetPosition();
                        person.curPos = nextPos;
                    }
                }
            }
        }
    }


    public boolean isPositionStillInGrid(int x, int y){
        return x >= startPos.x && x < getGridWidth() && y >= startPos.y && y < getGridHeight();
    }

    public boolean isPlaceEmpty(PositionVector pos){
        return persons.stream().noneMatch(person -> person.curPos.equals(pos));
    }

    public boolean isPlaceEmpty(int x , int y){
        return isPlaceEmpty(new PositionVector(x, y));
    }

    private Person getPersonOnPosition(PositionVector pos){
        return persons.stream().filter(person -> person.curPos.equals(pos)).findFirst().orElse(null);
    }

    public void addPerson(Person p){
        persons.add(p);
    }

    /**
     * @return The quadrant of the grid that the point falls into.
     *      0 for top-left, 1 for top-right, 2 for bottom-left, 3 for bottom-right.
     */
    private int getTargetGridId(int x, int y){
        int gridId = 0;
        if (x >= size) gridId += 1;
        if (y >= size) gridId += 2;
        return gridId;
    }

    private int getGridWidth(){
        return startPos.x + size;
    }

    private int getGridHeight(){
        return startPos.y + size;
    }
}
