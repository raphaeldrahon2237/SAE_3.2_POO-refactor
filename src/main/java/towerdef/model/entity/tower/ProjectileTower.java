package towerdef.model.entity.tower;

import towerdef.model.Game;
import towerdef.model.entity.upgrade.statsUpgrade;
import towerdef.model.entity.troop.enemy.Enemy;

public abstract class ProjectileTower extends Tower {

    public ProjectileTower(TowerCharacteristics characteristics, double x, double y, statsUpgrade statsUpgrade) {
        super(characteristics, x, y, statsUpgrade);
    }

    @Override
    public void attack(Game game){
        if (canAttack()){
            Enemy targetEnemy = game.findEnemyInRange(this.getX(), this.getY(), this.getRange());
            if(targetEnemy!=null) shootProjectileAtEnemy(targetEnemy, game, this.getAttackType());
            this.resetAttackTimer();
        }
    }
}
