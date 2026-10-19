package towerdef.model.entity.tower;

import towerdef.model.Game;
import towerdef.model.entity.AttackType;
import towerdef.model.entity.UpgradeStats;
import towerdef.model.entity.troop.Enemy;

public abstract class ProjectileTower extends Tower {

    public ProjectileTower(int towerTypeId, String description, int hp, int damage, int armor, int attackSpeed, int range, double x, double y,
                           AttackType attackType, int zone, int cost, int level, UpgradeStats upgradeStats) {
        super(towerTypeId, description, hp, damage, armor, attackSpeed, range, x, y,
                attackType, zone, cost, level, upgradeStats);
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
