package towerdef.model.entity.tower;

import towerdef.model.entity.AttackType;

public class Artillery extends Tower {



    public Artillery(int x, int y){
        super(2,"Degats de zone lents pour detruire les groupes d'ennemis.",45, 24, 2, 1, 6, x, y, AttackType.AOE, 2, 220, 1, 160, 8, 8, 2, 1);
    }
}
