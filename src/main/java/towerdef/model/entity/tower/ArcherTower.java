package towerdef.model.entity.tower;

import towerdef.model.entity.AttackType;
import towerdef.model.entity.UpgradeStats;


public class ArcherTower extends ProjectileTower {
    private static final UpgradeStats UPGRADES = new UpgradeStats(80,3, 5, 3, 1);


    private static final TowerCharacteristics CHARACTERISTICS = TowerCharacteristics.ARCHER;

    public ArcherTower(int x, int y) {
        super(CHARACTERISTICS.getTypeId(), CHARACTERISTICS.getDescription(), 35, CHARACTERISTICS.getDamage(),
                2, 3, CHARACTERISTICS.getRange(), x, y, AttackType.NORMAL, 0, CHARACTERISTICS.getCost(), 1,
               UPGRADES);
    }



    @Override
    public String toString() {
        return "Archer Tower: "+super.toString();
    }
}
