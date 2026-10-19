package towerdef.model.entity.tower;

import towerdef.model.entity.AttackType;
import towerdef.model.entity.UpgradeStats;
import towerdef.model.entity.troop.Enemy;

public class Mage extends ProjectileTower {

    private static final TowerCharacteristics CHARACTERISTICS = TowerCharacteristics.MAGE;

    private static final UpgradeStats UPGRADES = new UpgradeStats(120, 5, 5, 2, 1);
    public Mage( int x, int y){
        super(CHARACTERISTICS.getTypeId(), CHARACTERISTICS.getDescription(), 35, CHARACTERISTICS.getDamage(),
                1, 2, CHARACTERISTICS.getRange(), x, y, AttackType.MAGIC, 1, CHARACTERISTICS.getCost(), 1,
                UPGRADES);
    }

}
