package towerdef.model.entity.tower;

import towerdef.model.Game;
import towerdef.model.entity.AttackType;
import towerdef.model.entity.troop.Enemy;

public class Mage extends Tower {

    private int towerTypeId;

    public Mage( int x, int y){
        super(4,"Degats magiques de zone a moyenne portee.",35, 14, 1, 2, 5, x, y, AttackType.MAGIC, 1, 170, 1, 120, 5, 5, 3, 1);
    }

    public void act(Game game){
        if(canAttack()){
            Enemy targetEnemy = game.findEnemyInRange(this.getX(), this.getY(), this.getRange());
            if(targetEnemy!=null) shootProjectileAtEnemy(targetEnemy, game, this.getAttackType());
            this.resetAttackTimer();
        }
    }
}
