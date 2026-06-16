package towerdef.model.entity.troop;

public class Recruit extends Troop {

    public Recruit(int x, int y) {
        super(18, 4, 1, 2, 1, 2, x, y);
    }
    @Override
    public String toString() {
        return "Recruit: " + super.toString();
    }
}

