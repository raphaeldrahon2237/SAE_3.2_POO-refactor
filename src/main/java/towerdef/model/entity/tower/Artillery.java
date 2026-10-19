package towerdef.model.entity.tower;

import towerdef.model.entity.AttackType;

public class Artillery extends ProjectileTower {

    private static final TowerCharacteristics CHARACTERISTICS = TowerCharacteristics.ARTILLERY;



    public Artillery(int x, int y){
        super(CHARACTERISTICS.getTypeId(), CHARACTERISTICS.getDescription(), 45, CHARACTERISTICS.getDamage(),
                2, 1, CHARACTERISTICS.getRange(), x, y, AttackType.AOE, 2, CHARACTERISTICS.getCost(), 1,
                160, 8, 8, 1, 1);
    }

}
