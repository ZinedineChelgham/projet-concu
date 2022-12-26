package simulator;

import java.util.List;
import java.util.Objects;


public record Field(List<Person> persons) {

    public Field(List<Person> persons) {
        this.persons = persons;
        setFieldForPersons();

    }

    public List<Person> getPersons() {
        return persons;
    }

    private void setFieldForPersons() {
        for (Person p : persons) p.setSharedField(this);
    }


    public boolean isPlaceFree(int x, int y) {
        for (Person p : persons) {
            if (p.x == x && p.y == y)
                return false;
        }
        return true;
    }

    public Person getPersonAt(int x, int y) {
        for (Person p : persons) {
            if (p.x == x && p.y == y)
                return p;
        }
        return null;
    }

    public void checkTheArrived(){
        for (Person p : persons){
            if(p.isArrived()) p.setPosOutside();
        }
    }


    public void resetPersonAt(int nextXPos, int nextYPos) {
        Objects.requireNonNull(getPersonAt(nextXPos, nextYPos)).resetPosition();
    }
}
