package towerdef.model.entity.tower;

import towerdef.model.Game;
import towerdef.model.entity.AttackType;
import towerdef.model.entity.UpgradeStats;
import towerdef.model.entity.troop.Enemy;

public class Artillery extends Tower {

    private static final UpgradeStats UPGRADES = new UpgradeStats(160, 8, 8, 1, 1);

    public Artillery(int x, int y){
        super(2,"Big area damage, generalist",45, 24, 2, 1, 6, x, y, AttackType.AOE, 2, 220, 1,UPGRADES);
    }

    public void attack(Game game){
        if(canAttack()){
            Enemy targetEnemy = game.findEnemyInRange(this.getX(), this.getY(), this.getRange());
            if(targetEnemy!=null) shootProjectileAtEnemy(targetEnemy, game, this.getAttackType());
            this.resetAttackTimer();
        }
    }
}
