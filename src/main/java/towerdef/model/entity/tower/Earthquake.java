package towerdef.model.entity.tower;

import towerdef.model.Game;
import towerdef.model.entity.UpgradeStats;
import towerdef.model.entity.troop.enemy.Enemy;

import java.util.ArrayList;

public class Earthquake extends Tower {

    private static final UpgradeStats UPGRADES = new UpgradeStats(180, 2, 10, 1, 1);

    private int slowSpeed;

    public Earthquake(int x, int y, int slowSpeed) {
        super(TowerCharacteristics.EARTHQUAKE, x, y, UPGRADES);
        this.slowSpeed = slowSpeed;
    }


    public void attack(Game game){
        ArrayList<Enemy> enemiesInRange;
        this.incrementAttackCounter();
        if (canAttack()){
            enemiesInRange = game.findEnemiesInRange(this.getX(), this.getY(), this.getRange());
            if (!enemiesInRange.isEmpty()){
                for(Enemy enemy: enemiesInRange){
                    enemy.takeDamage(this.getDamage());
                    enemy.setMooveSpeed(slowSpeed);
                }

            }
            this.resetAttackTimer();
        }

    }
}
