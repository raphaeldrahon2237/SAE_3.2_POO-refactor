package towerdef.model.entity.tower;

import towerdef.model.entity.UpgradeStats;
import towerdef.model.entity.troop.Enemy;

public class Mage extends ProjectileTower {


    private static final UpgradeStats UPGRADES = new UpgradeStats(120, 5, 5, 2, 1);
    public Mage( int x, int y){
        super(TowerCharacteristics.MAGE, x, y, UPGRADES);
    }

}
