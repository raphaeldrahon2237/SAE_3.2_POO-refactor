package towerdef.model.entity.tower;

import towerdef.model.entity.AttackType;

public class Mage extends ProjectileTower {

    private static final TowerCharacteristics CHARACTERISTICS = TowerCharacteristics.MAGE;

    public Mage( int x, int y){
        super(CHARACTERISTICS.getTypeId(), CHARACTERISTICS.getDescription(), 35, CHARACTERISTICS.getDamage(),
                1, 2, CHARACTERISTICS.getRange(), x, y, AttackType.MAGIC, 1, CHARACTERISTICS.getCost(), 1,
                120, 5, 5, 2, 1);
    }

}
