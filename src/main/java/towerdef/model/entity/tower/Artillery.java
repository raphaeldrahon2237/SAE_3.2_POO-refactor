package towerdef.model.entity.tower;

import towerdef.model.entity.upgrade.statsUpgrade;

public class Artillery extends ProjectileTower {


    private static final statsUpgrade UPGRADES = new statsUpgrade(160, 8, 8, 1, 1);

    public Artillery(int x, int y){
        super(TowerCharacteristics.ARTILLERY, x, y, UPGRADES);
    }

}
