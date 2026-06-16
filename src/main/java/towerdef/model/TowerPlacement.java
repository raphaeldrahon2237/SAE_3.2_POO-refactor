package towerdef.model;

import towerdef.model.entity.EntityType;
import towerdef.model.entity.tower.Tower;
import towerdef.model.entity.tower.TowerFactory;
import towerdef.model.entity.troop.Recruit;
import towerdef.model.entity.troop.Troop;
import towerdef.model.map.MapGrid;

public class TowerPlacement {

    private MapGrid mapGrid;
    private GameEntities gameEntities;
    private Economy economy;

    public TowerPlacement(MapGrid mapGrid, GameEntities gameEntities, Economy economy) {
        this.mapGrid = mapGrid;
        this.gameEntities = gameEntities;
        this.economy = economy;
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

        if (tower == null || economy.getGold() < tower.getCost()) {
            return false;
        }

        economy.spendGold(tower.getCost());
        gameEntities.addTower(tower);

        if (entityType == EntityType.BARRACK) {
            Troop recruit = createRecruitNextToBarrack(x, y);

            if (recruit != null) {
                gameEntities.addAlly(recruit);
            }
        }

        return true;
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
