package towerdef.model.entity.tower;

import towerdef.model.entity.upgrade.statsUpgrade;


public class ArcherTower extends ProjectileTower {
    private static final statsUpgrade UPGRADES = new statsUpgrade(80,3, 5, 3, 1);



    public ArcherTower(int x, int y) {
        super(TowerCharacteristics.ARCHER, x, y, UPGRADES);
    }



    @Override
    public String toString() {
        return "Archer Tower: "+super.toString();
    }
}
