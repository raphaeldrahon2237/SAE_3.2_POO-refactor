package towerdef.model;

import towerdef.model.entity.EntityType;
import towerdef.model.entity.tower.Tower;
import towerdef.model.entity.tower.TowerFactory;
import towerdef.model.map.MapGrid;

public class TowerManager {

    private GameEntities gameEntities;
    private Progression progression;
    private TowerPlacement towerPlacement;

    public TowerManager(MapGrid mapGrid, GameEntities gameEntities, Progression progression) {
        this.gameEntities = gameEntities;
        this.progression = progression;
        towerPlacement = new TowerPlacement(mapGrid, gameEntities, progression);
    }

    public boolean canPlaceTower(int x, int y) {
        return towerPlacement.canPlaceTower(x, y);
    }

    public boolean placeTower(int x, int y, int idTower) {
        boolean placed = towerPlacement.placeTower(x, y, idTower);

        if (placed) {
            progression.towerPlaced();
        }

        return placed;
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
}
