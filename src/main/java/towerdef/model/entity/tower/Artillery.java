package towerdef.model.entity.tower;

import towerdef.model.Game;
import towerdef.model.entity.AttackType;
import towerdef.model.entity.troop.Enemy;

public class Artillery extends Tower {



    public Artillery(int x, int y){
        super(2,"Degats de zone lents pour detruire les groupes d'ennemis.",45, 24, 2, 1, 6, x, y, AttackType.AOE, 2, 220, 1, 160, 8, 8, 2, 1);
    }

    public void act(Game game){
        if(canAttack()){
            Enemy targetEnemy = game.findEnemyInRange(this.getX(), this.getY(), this.getRange());
            if(targetEnemy!=null) shootProjectileAtEnemy(targetEnemy, game, this.getAttackType());
            this.resetAttackTimer();
        }
    }
}
