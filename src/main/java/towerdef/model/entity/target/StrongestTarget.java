package towerdef.model.entity.target;

import towerdef.model.entity.tower.Tower;
import towerdef.model.entity.troop.enemy.Enemy;

import java.util.ArrayList;

public class StrongestTarget implements TargetStrategy {

    @Override
    public Enemy chooseTarget(ArrayList<Enemy> enemiesInRange, Tower tower) {
        Enemy strongestEnemy = null;

        for (Enemy enemy : enemiesInRange) {
            if (strongestEnemy == null || enemy.getHp() > strongestEnemy.getHp()) {
               strongestEnemy = enemy;
            }
        }
        return strongestEnemy;
    }
}

