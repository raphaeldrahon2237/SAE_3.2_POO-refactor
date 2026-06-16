package towerdef.model;

import towerdef.model.entity.Base;
import towerdef.model.entity.Entity;
import towerdef.model.entity.tower.Tower;
import towerdef.model.entity.troop.Enemy;
import towerdef.model.entity.troop.Troop;

import java.util.ArrayList;

public class RangeChecker {

    private GameEntities gameEntities;
    private Base base;

    public RangeChecker(GameEntities gameEntities, Base base) {
        this.gameEntities = gameEntities;
        this.base = base;
    }

    public Enemy findEnemyInRange(double x, double y, int range) {
        Enemy closestEnemy = null;
        double closestDistance = Double.MAX_VALUE;

        for (Enemy enemy : gameEntities.getEnemies()) {
            double distance = Utils.distance(enemy.getX(), enemy.getY(), x, y);

            if (enemy.isAlive() && distance <= range && distance < closestDistance) {
                closestEnemy = enemy;
                closestDistance = distance;
            }
        }

        return closestEnemy;
    }

    public ArrayList<Enemy> findEnemiesInRange(double x, double y, int range) {
        ArrayList<Enemy> enemiesInRange = new ArrayList<>();

        for (Enemy enemy : gameEntities.getEnemies()) {
            if (enemy.isAlive() && Utils.isInRange(enemy.getX(), enemy.getY(), x, y, range)) {
                enemiesInRange.add(enemy);
            }
        }

        return enemiesInRange;
    }

    public Tower findTowerInRange(double x, double y, int range) {
        Tower closestTower = null;
        double closestDistance = Double.MAX_VALUE;

        for (Tower tower : gameEntities.getTowers()) {
            double distance = Utils.distance(tower.getX(), tower.getY(), x, y);

            if (tower.isAlive() && distance <= range && distance < closestDistance) {
                closestTower = tower;
                closestDistance = distance;
            }
        }

        return closestTower;
    }

    public Troop findAlliedTroopInRange(double x, double y, int range) {
        Troop closestTroop = null;
        double closestDistance = Double.MAX_VALUE;

        for (Entity ally : gameEntities.getAllies()) {
            if (ally instanceof Troop troop) {
                double distance = Utils.distance(troop.getX(), troop.getY(), x, y);

                if (troop.isAlive() && distance <= range && distance < closestDistance) {
                    closestTroop = troop;
                    closestDistance = distance;
                }
            }
        }

        return closestTroop;
    }

    public boolean isBaseInRange(Enemy enemy) {
        return Utils.isInRange(base.getX(), base.getY(), enemy.getX(), enemy.getY(), enemy.getRange());
    }

    public boolean isTowerInRange(double x, double y, int range) {
        return findTowerInRange(x, y, range) != null;
    }

    public boolean isAlliedTroopInRange(double x, double y, int range) {
        return findAlliedTroopInRange(x, y, range) != null;
    }
}
