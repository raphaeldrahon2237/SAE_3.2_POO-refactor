package towerdef.model.entity.target;

import towerdef.model.Utils;
import towerdef.model.entity.tower.Tower;
import towerdef.model.entity.troop.enemy.Enemy;

import java.util.ArrayList;

public class ClosestTarget implements TargetStrategy {

    @Override
    public Enemy chooseTarget(ArrayList<Enemy> enemiesInRange, Tower tower) {
        Enemy closestEnemy = null;
        double closestDistance = Double.MAX_VALUE;

        for (Enemy enemy : enemiesInRange) {
            double distance = Utils.distance(enemy.getX(), enemy.getY(), tower.getX(), tower.getY());
            if (distance < closestDistance) {
                closestEnemy = enemy;
                closestDistance = distance;
            }
        }
        return closestEnemy;
    }
}
