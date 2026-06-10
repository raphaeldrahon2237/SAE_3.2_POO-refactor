package towerdef.model.entity.tower;

import towerdef.model.entity.AttackType;

public class ArcherTower extends Tower {

    public ArcherTower(int x, int y) {
        super(1,"Tir rapide monocible pour eliminer les ennemis faibles a distance.",35, 8, 2, 4, 4, x, y,
                AttackType.NORMAL, 0, 120, 1,
                80, 3, 5, 5, 1);
    }

    @Override
    public String toString() {
        return "Archer Tower: "+super.toString();
    }
}
