package towerdef.model.entity.troop;

public class Wolf extends Enemy {

    public Wolf(int x, int y) {
        super(18, 3, 0, 2, 1, 3, x, y, 15);
    }

    @Override
    public String toString() {
        return "Wolf: " + super.toString();
    }
}
