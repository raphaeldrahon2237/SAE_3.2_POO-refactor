package towerdef.model.entity.troop;

public class Wolf extends Enemy {

    public Wolf(int x, int y) {
        super(3, 2, 0, 5, 1, 6, x, y, 1);
    }

    public String toString(){
        return "Wolf: "+super.toString();
    }
}
