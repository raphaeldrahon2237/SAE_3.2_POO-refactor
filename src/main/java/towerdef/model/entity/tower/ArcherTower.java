package towerdef.model.entity.tower;

import towerdef.model.entity.AttackType;

public class ArcherTower extends Tower {

    public ArcherTower(int x, int y) {
        super(20, 5, 2, 4, 3, x, y, 15,AttackType.NORMAL);
    }

    @Override
    public String toString() {
        return "Archer Tower: "+super.toString();
    }
}
