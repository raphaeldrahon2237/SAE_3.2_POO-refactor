package towerdef.model.entity.troop;

public class Soldier extends Troop {
    public Soldier(int x, int y) {
        super(25, 6, 3, 3, 1, 2, x, y);
    }
    @Override
    public String toString() {
        return "Soldier: " + super.toString();
    }
}
