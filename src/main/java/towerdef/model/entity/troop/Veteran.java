package towerdef.model.entity.troop;

public class Veteran extends Troop {
    public Veteran(int x, int y) {
        super(30, 6, 6, 3, 1, 2, x, y);
    }
    @Override
    public String toString() {
        return "Veteran: " + super.toString();
    }
}
