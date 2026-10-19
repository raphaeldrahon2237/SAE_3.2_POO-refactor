package towerdef.model.entity.tower;

import towerdef.model.entity.UpgradeStats;


public class ArcherTower extends ProjectileTower {
    private static final UpgradeStats UPGRADES = new UpgradeStats(80,3, 5, 3, 1);



    public ArcherTower(int x, int y) {
        super(TowerCharacteristics.ARCHER, x, y, UPGRADES);
    }



    @Override
    public String toString() {
        return "Archer Tower: "+super.toString();
    }
}
