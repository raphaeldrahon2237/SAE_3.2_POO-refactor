package towerdef.model.entity.tower;

import towerdef.model.entity.upgrade.statsUpgrade;

public class Mage extends ProjectileTower {


    private static final statsUpgrade UPGRADES = new statsUpgrade(120, 5, 5, 2, 1);
    public Mage( int x, int y){
        super(TowerCharacteristics.MAGE, x, y, UPGRADES);
    }

}
