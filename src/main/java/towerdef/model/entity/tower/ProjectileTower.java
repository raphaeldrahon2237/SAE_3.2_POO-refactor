package towerdef.model.entity.tower;

import towerdef.model.Game;
import towerdef.model.entity.upgrade.StatsUpgrade;
import towerdef.model.entity.troop.enemy.Enemy;
import towerdef.model.entity.upgrade.Upgrade;

import java.util.ArrayList;
import java.util.List;

public abstract class ProjectileTower extends Tower {

    public ProjectileTower(TowerCharacteristics characteristics, double x, double y, List<Upgrade> upgradePath, int upgradeCost) {
        super(characteristics, x, y, upgradePath, upgradeCost);
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
