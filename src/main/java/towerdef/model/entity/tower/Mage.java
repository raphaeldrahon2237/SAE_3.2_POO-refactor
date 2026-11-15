package towerdef.model.entity.tower;

import towerdef.model.entity.upgrade.StatsUpgrade;
import towerdef.model.entity.upgrade.Upgrade;

import java.util.List;

public class Mage extends ProjectileTower {


    private static final StatsUpgrade UPGRADES = new StatsUpgrade( 5, 5, 2, 1);
    private static final List<Upgrade> upgradePath = List.of(UPGRADES, UPGRADES, UPGRADES, UPGRADES);
    public Mage( int x, int y){
        super(TowerCharacteristics.MAGE, x, y, upgradePath, 120);
    }

}
