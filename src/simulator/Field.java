package simulator;

import java.util.List;
import java.util.Objects;


public record Field(List<Person> persons) {


    public Field(List<Person> persons) {
        this.persons = persons;
        setFieldForPersons();

    }

    public synchronized void placePerson(Person p, PositionVector desiredPos) {
        if(!isPlaceFree(desiredPos.x, desiredPos.y)) {
            Person p2 = Objects.requireNonNull(getPersonAt(desiredPos));
            if(p.id > p2.id){
                p2.resetPosition();
            }
        }
        p.curPos = desiredPos.myClone();
    }

    private void setFieldForPersons() {
        for (Person p : persons) p.setSharedField(this);
    }
    public List<Person> getPersons() {
        return persons;

    }

    public boolean isPlaceFree(int x, int y) {
        for (Person p : persons) {
            if (p.curPos.equals(new PositionVector(x, y)))
                return false;
        }
        return true;
    }

    public Person getPersonAt(PositionVector pos) {
        for (Person p : persons) {
            if (p.curPos.equals(pos))
                return p;
        }
        return null;
    }

    public void checkTheArrived(){
        for (Person p : persons){
            if(p.isArrived()) p.setPosOutside();
        }
    }

}
