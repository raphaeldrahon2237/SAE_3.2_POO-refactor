package towerdef.model.entity.tower;

import towerdef.model.entity.AttackType;

public class ArcherTower extends Tower {

    public ArcherTower(int x, int y) {
        super(40, 10, 5, 2, 2, x, y, AttackType.NORMAL, 1, 20, 1, 25, 5, 5, 1, 2);
    }

    @Override
    public String toString() {
        return "Archer Tower: "+super.toString();
    }
}
