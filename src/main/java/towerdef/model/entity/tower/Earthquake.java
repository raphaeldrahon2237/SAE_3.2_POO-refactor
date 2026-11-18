package towerdef.model.entity.tower;

import towerdef.model.Game;
import towerdef.model.entity.upgrade.StatsUpgrade;
import towerdef.model.entity.troop.enemy.Enemy;
import towerdef.model.entity.upgrade.Upgrade;

import java.util.ArrayList;
import java.util.List;

public class Earthquake extends Tower {

    public static final int SLOW_SPEED = 1;

    private static final StatsUpgrade UPGRADES = new StatsUpgrade( 10, 2, 1, 1);
    private static final List<Upgrade> upgradePath = List.of(UPGRADES, UPGRADES, UPGRADES, UPGRADES);

    private int slowSpeed;

    public Earthquake(int x, int y) {
        super(TowerCharacteristics.EARTHQUAKE, x, y, upgradePath, 180);
        this.slowSpeed = SLOW_SPEED;
    }


    public void attack(Game game){
        ArrayList<Enemy> enemiesInRange;
        if (canAttack()){
            this.incrementAttackCounter();
            enemiesInRange = game.findEnemiesInRange(this.getX(), this.getY(), this.getRange());
            if (!enemiesInRange.isEmpty()){
                for(Enemy enemy: enemiesInRange){
                    enemy.takeDamage(this.getDamage());
                    enemy.setMoveSpeed(slowSpeed);
                }

            }
            this.resetAttackTimer();
        }

    }
}
