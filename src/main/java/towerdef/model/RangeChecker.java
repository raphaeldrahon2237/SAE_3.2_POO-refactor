package towerdef.model;

import towerdef.model.entity.Base;
import towerdef.model.entity.Entity;
import towerdef.model.entity.tower.Tower;
import towerdef.model.entity.troop.enemy.Enemy;
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
        ArrayList<Entity> enemies = new ArrayList<>(gameEntities.getEnemies());
        return (Enemy) findClosestInRange(enemies, x, y, range);
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
        ArrayList<Entity> towers = new ArrayList<>(gameEntities.getTowers());
        return (Tower) findClosestInRange(towers, x, y, range);
    }

    public Troop findAlliedTroopInRange(double x, double y, int range) {
        ArrayList<Entity> troops = new ArrayList<>();

        for (Entity ally : gameEntities.getAllies()) {
            if (ally instanceof Troop) {
               troops.add(ally);
            }
        }
        return (Troop) findClosestInRange(troops, x, y , range);
    }

    private Entity findClosestInRange(ArrayList<Entity> entities, double x, double y, int range) {
        Entity closestEntity = null;
        double closestDistance = Double.MAX_VALUE;

        for (Entity entity : entities) {
            double distance = Utils.distance(entity.getX(), entity.getY(), x, y);

            if (entity.isAlive() && distance <= range && distance < closestDistance) {
                closestEntity = entity;
                closestDistance = distance;
            }
        }
        return closestEntity;
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
