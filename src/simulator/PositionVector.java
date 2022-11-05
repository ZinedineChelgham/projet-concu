package simulator;

public class PositionVector {
    protected int x, y;

    PositionVector(int x, int y){
        this.x = x;
        this.y = y;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionVector that = (PositionVector) o;
        return x == that.x && y == that.y;
    }

    public PositionVector cloneVector(){
        return new PositionVector(x,y);
    }
}
