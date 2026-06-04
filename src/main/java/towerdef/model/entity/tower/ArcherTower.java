package towerdef.model.entity.tower;

public class ArcherTower extends Tower {

    public ArcherTower(int x, int y) {
        super(20, 5, 2, 4, 3, x, y, 10);
    }

    @Override
    public String toString() {
        return "Archer Tower: "+super.toString();
    }
}