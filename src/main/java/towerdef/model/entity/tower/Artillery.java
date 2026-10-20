package towerdef.model.entity.tower;

import towerdef.model.entity.UpgradeStats;

public class Artillery extends ProjectileTower {


    private static final UpgradeStats UPGRADES = new UpgradeStats(160, 8, 8, 1, 1);

    public Artillery(int x, int y){
        super(TowerCharacteristics.ARTILLERY, x, y, UPGRADES);
    }

}
