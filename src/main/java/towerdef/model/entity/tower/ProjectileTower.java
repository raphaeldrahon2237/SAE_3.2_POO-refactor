package towerdef.model.entity.tower;

import towerdef.model.Game;
import towerdef.model.entity.UpgradeStats;
import towerdef.model.entity.troop.Enemy;

public abstract class ProjectileTower extends Tower {

    public ProjectileTower(TowerCharacteristics characteristics, double x, double y, UpgradeStats upgradeStats) {
        super(characteristics, x, y, upgradeStats);
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
