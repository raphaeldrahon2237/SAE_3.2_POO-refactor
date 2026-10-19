package towerdef.model.entity.tower;

import towerdef.model.entity.AttackType;


public class ArcherTower extends ProjectileTower {

    private static final TowerCharacteristics CHARACTERISTICS = TowerCharacteristics.ARCHER;

    public ArcherTower(int x, int y) {
        super(CHARACTERISTICS.getTypeId(), CHARACTERISTICS.getDescription(), 35, CHARACTERISTICS.getDamage(),
                2, 3, CHARACTERISTICS.getRange(), x, y, AttackType.NORMAL, 0, CHARACTERISTICS.getCost(), 1,
                80, 3, 5, 3, 1);
    }



    @Override
    public String toString() {
        return "Archer Tower: "+super.toString();
    }
}
