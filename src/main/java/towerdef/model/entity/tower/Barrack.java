package towerdef.model.entity.tower;

public class Barrack extends Tower {
    public Barrack(int x, int y){
        super(50, 0, 8, 0,0, x, y, 20);
    }

    public String toString() {
        return "Barracks: "+super.toString();
    }
}
