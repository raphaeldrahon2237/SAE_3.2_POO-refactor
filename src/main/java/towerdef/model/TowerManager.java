package towerdef.model;

import towerdef.model.entity.Entity;
import towerdef.model.entity.EntityType;
import towerdef.model.entity.tower.Barrack;
import towerdef.model.entity.tower.Tower;
import towerdef.model.entity.tower.TowerCharacteristics;
import towerdef.model.entity.tower.TowerFactory;
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
            Barrack barrack = (Barrack) tower;
            Troop recruitedTroop = createTroopNextToBarrack(barrack);

            if (recruitedTroop != null) {
                gameEntities.addAlly(recruitedTroop);
                barrack.setRecruitedTroop(recruitedTroop);
            }
        }

        progression.towerPlaced();

        return true;
    }

    public TowerCharacteristics getTowerCharacteristics(int towerTypeId) {
        return TowerCharacteristics.fromId(towerTypeId);
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



    // Updates the respawn of recruited troops for all barracks.
    public void updateBarrackRespawns(int currentWave) {
        for (Tower tower : gameEntities.getTowers()) {
            if (tower instanceof Barrack barrack) {
                updateBarrackRecruitedTroop(barrack, currentWave);
            }
        }
    }

    private void updateBarrackRecruitedTroop(Barrack barrack, int currentWave) {
        if (barrack.hasRecruitedTroop() && findRecruitedTroopById(barrack.getRecruitedTroopId()) == null) {
            barrack.markRecruitedTroopDead(currentWave);
        }

        if (barrack.canRespawnRecruitedTroop(currentWave)) {
            Troop recruitedTroop = createTroopNextToBarrack(barrack);

            if (recruitedTroop != null) {
                gameEntities.addAlly(recruitedTroop);
                barrack.setRecruitedTroop(recruitedTroop);
            }
        }
    }
    // Marks the barrack troop as dead if missing, then respawns it when allowed.
    private Troop findRecruitedTroopById(int recruitedTroopId) {
        for (Entity ally : gameEntities.getAllies()) {
            if (ally instanceof Troop troop && troop.getId() == recruitedTroopId && troop.isAlive()) {
                return troop;
            }
        }

        return null;
    }

    // Creates a recruited troop on a walkable tile next to the barrack.
    private Troop createTroopNextToBarrack(Barrack barrack) {
        int x = (int) barrack.getX();
        int y = (int) barrack.getY();

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
                return barrack.createTroopForCurrentLevel(newX, newY);
            }
        }

        return null;
    }
}
