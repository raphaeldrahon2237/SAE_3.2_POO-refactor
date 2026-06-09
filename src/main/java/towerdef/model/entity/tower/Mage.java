package towerdef.model.entity.tower;

import towerdef.model.entity.AttackType;

public class Mage extends Tower {
    public Mage( int x, int y){
        super(35, 8, 4, 2, 4, x, y, AttackType.AOE, 1, 20, 1, 25, 5, 5, 1, 2);
    }
}
