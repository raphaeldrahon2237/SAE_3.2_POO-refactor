package towerdef.model.entity.tower;

import towerdef.model.entity.upgrade.StatsUpgrade;
import towerdef.model.entity.upgrade.Upgrade;

import java.util.List;

public class Artillery extends ProjectileTower {


    private static final StatsUpgrade UPGRADES = new StatsUpgrade(8, 8, 1, 1);
    private static final List<Upgrade> upgradePath = List.of(UPGRADES, UPGRADES, UPGRADES, UPGRADES);

    public Artillery(int x, int y){
        super(TowerCharacteristics.ARTILLERY, x, y, upgradePath, 160);
    }

}
