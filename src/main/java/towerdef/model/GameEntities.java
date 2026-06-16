package towerdef.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import towerdef.model.entity.Entity;
import towerdef.model.entity.Projectile;
import towerdef.model.entity.tower.Tower;
import towerdef.model.entity.troop.Enemy;

public class GameEntities {

    private ObservableList<Entity> allies;
    private ObservableList<Enemy> enemies;
    private ObservableList<Tower> towers;
    private ObservableList<Projectile> projectiles;

    public GameEntities() {
        allies = FXCollections.observableArrayList();
        enemies = FXCollections.observableArrayList();
        towers = FXCollections.observableArrayList();
        projectiles = FXCollections.observableArrayList();
    }

    public void addAlly(Entity ally) {
        allies.add(ally);
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void addTower(Tower tower) {
        towers.add(tower);
        allies.add(tower);
    }

    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }

    public void removeProjectile(Projectile projectile) {
        projectiles.remove(projectile);
    }

    public void removeTower(Tower tower) {
        towers.remove(tower);
        tower.killTower();
        allies.remove(tower);
    }

    public void removeDeadEntities() {
        allies.removeIf(ally -> !ally.isAlive());
        enemies.removeIf(enemy -> !enemy.isAlive());
        towers.removeIf(tower -> !tower.isAlive());

    }

    public ObservableList<Entity> getAllies() {
        return allies;
    }

    public ObservableList<Enemy> getEnemies() {
        return enemies;
    }

    public ObservableList<Tower> getTowers() {
        return towers;
    }

    public ObservableList<Projectile> getProjectiles() {
        return projectiles;
    }

    public Tower getTowerById(int id) {
        for (Tower tower : towers) {
            if (tower.getId() == id) {
                return tower;
            }
        }
        return null;
    }
}
