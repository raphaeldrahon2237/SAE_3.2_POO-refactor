package towerdef.model.entity.tower;

import towerdef.model.entity.AttackType;
import towerdef.model.entity.UpgradeStats;
import towerdef.model.entity.troop.Enemy;

public class Artillery extends ProjectileTower {

    private static final TowerCharacteristics CHARACTERISTICS = TowerCharacteristics.ARTILLERY;

    private static final UpgradeStats UPGRADES = new UpgradeStats(160, 8, 8, 1, 1);

    public Artillery(int x, int y){
        super(CHARACTERISTICS.getTypeId(), CHARACTERISTICS.getDescription(), 45, CHARACTERISTICS.getDamage(),
                2, 1, CHARACTERISTICS.getRange(), x, y, AttackType.AOE, 2, CHARACTERISTICS.getCost(), 1,
                UPGRADES);
    }

}
