package towerdef.model.entity.tower;

import towerdef.model.Game;
import towerdef.model.entity.AttackType;
import towerdef.model.entity.UpgradeStats;
import towerdef.model.entity.troop.Enemy;

public class Mage extends Tower {

    private static final UpgradeStats UPGRADES = new UpgradeStats(120, 5, 5, 2, 1);
    public Mage( int x, int y){
        super(4,"Magic damage ignores enemy armor, good at killing strong enemies",35, 14, 1, 2, 5, x, y, AttackType.MAGIC, 1, 170, 1, UPGRADES);
    }

    public void attack(Game game){
        if(canAttack()){
            Enemy targetEnemy = game.findEnemyInRange(this.getX(), this.getY(), this.getRange());
            if(targetEnemy!=null) shootProjectileAtEnemy(targetEnemy, game, this.getAttackType());
            this.resetAttackTimer();
        }
    }
}
