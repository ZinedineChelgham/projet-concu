package simulator;

public class PositionVector {
    protected int x, y;

    PositionVector(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void add(PositionVector pv){
        this.x += pv.x;
        this.y += pv.y;
    }

    public void add(int x, int y){
        this.x += x;
        this.y += y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionVector that = (PositionVector) o;
        return x == that.x && y == that.y;
    }

    public PositionVector myClone(){
        return new PositionVector(x,y);
    }
}