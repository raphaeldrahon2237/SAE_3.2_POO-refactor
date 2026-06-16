package towerdef.model;

import towerdef.model.entity.EntityType;
import towerdef.model.entity.tower.Tower;
import towerdef.model.entity.tower.TowerFactory;
import towerdef.model.entity.troop.Recruit;
import towerdef.model.entity.troop.Troop;
import towerdef.model.map.MapGrid;

public class TowerManager {

    private MapGrid mapGrid;
    private GameEntities gameEntities;
    private Progression progression;

    public TowerManager(MapGrid mapGrid, GameEntities gameEntities, Progression progression) {
        this.mapGrid = mapGrid;
        this.gameEntities = gameEntities;
        this.progression = progression;
    }

    public boolean canPlaceTower(int x, int y) {
        if (!mapGrid.isInside(x, y)) {
            return false;
        }

        if (mapGrid.isWalkable(x, y)) {
            return false;
        }

        for (Tower tower : gameEntities.getTowers()) {
            if (tower.getX() == x && tower.getY() == y) {
                return false;
            }
        }

        return true;
    }

    public boolean placeTower(int x, int y, int idTower) {
        if (!canPlaceTower(x, y)) {
            return false;
        }

        EntityType entityType = EntityType.fromId(idTower);

        if (entityType == null) {
            return false;
        }

        Tower tower = TowerFactory.createTower(entityType, x, y);

        if (tower == null || progression.getGold() < tower.getCost()) {
            return false;
        }

        progression.spendGold(tower.getCost());
        gameEntities.addTower(tower);

        if (entityType == EntityType.BARRACK) {
            Troop recruit = createRecruitNextToBarrack(x, y);

            if (recruit != null) {
                gameEntities.addAlly(recruit);
            }
        }

        progression.towerPlaced();

        return true;
    }

    public Tower getTowerByTypeId(int towerTypeId) {
        EntityType type = EntityType.fromId(towerTypeId);
        Tower tower = null;

        if (type != null) {
            tower = TowerFactory.createTower(type, 0, 0);
        }

        return tower;
    }

    public Tower getTowerAt(int x, int y) {
        Tower selectedTower = null;

        for (Tower tower : gameEntities.getTowers()) {
            if (tower.isAlive() && (int) tower.getX() == x && (int) tower.getY() == y) {
                selectedTower = tower;
            }
        }

        return selectedTower;
    }

    public boolean upgradeTower(Tower tower) {
        boolean upgraded = false;

        if (tower != null && tower.isAlive() && tower.levelProperty().get() < 5 && progression.getGold() >= tower.getUpgradeCost()) {
            progression.spendGold(tower.getUpgradeCost());
            tower.upgrade();
            upgraded = true;
        }

        return upgraded;
    }

    public boolean sellTower(Tower tower) {
        boolean sold = false;

        if (tower != null && tower.isAlive()) {
            progression.addGold(tower.getSellValue());
            gameEntities.removeTower(tower);
            sold = true;
        }

        return sold;
    }

    private Troop createRecruitNextToBarrack(int x, int y) {
        int[][] directions = {
                {0, -1},
                {0, 1},
                {-1, 0},
                {1, 0}
        };

        for (int[] direction : directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];

            if (mapGrid.isInside(newX, newY) && mapGrid.isWalkable(newX, newY)) {
                return new Recruit(newX, newY);
            }
        }

        return null;
    }
}
