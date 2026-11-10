package towerdef.model.entity.tower;

import towerdef.model.entity.upgrade.StatsUpgrade;
import towerdef.model.entity.upgrade.Upgrade;

import java.util.List;


public class ArcherTower extends ProjectileTower {
    private static final StatsUpgrade UPGRADES = new StatsUpgrade(3, 5, 3, 1);
    private static final List<Upgrade> upgradePath = List.of(UPGRADES, UPGRADES, UPGRADES, UPGRADES);



    public ArcherTower(int x, int y) {
        super(TowerCharacteristics.ARCHER, x, y, upgradePath, 80);
    }


    @Override
    public String toString() {
        return "Archer Tower: "+super.toString();
    }
}
