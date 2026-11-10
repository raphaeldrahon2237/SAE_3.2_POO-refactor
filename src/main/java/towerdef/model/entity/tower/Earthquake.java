package towerdef.model.entity.tower;

import towerdef.model.Game;
import towerdef.model.entity.upgrade.statsUpgrade;
import towerdef.model.entity.troop.enemy.Enemy;

import java.util.ArrayList;

public class Earthquake extends Tower {

    public static final int SLOW_SPEED = 1;

    private static final statsUpgrade UPGRADES = new statsUpgrade(180, 2, 10, 1, 1);

    private int slowSpeed;

    public Earthquake(int x, int y) {
        super(TowerCharacteristics.EARTHQUAKE, x, y, UPGRADES);
        this.slowSpeed = SLOW_SPEED;
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
