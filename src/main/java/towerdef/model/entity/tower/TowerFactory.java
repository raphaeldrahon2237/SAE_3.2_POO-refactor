package towerdef.model.entity.tower;

import towerdef.model.entity.EntityType;

public class TowerFactory {

    public static Tower createTower(EntityType entityType, int x, int y) {
        Tower tower = null;

        switch (entityType) {
            case ARCHERTOWER -> tower = new ArcherTower(x, y);
            case MAGE -> tower = new Mage(x, y);
            case BARRACK -> tower = new Barrack(x, y);
            case ARTILLERY -> tower = new Artillery(x, y);
            case SHOCKWAVE -> tower = new Earthquake(x, y,2);
            default -> tower = null;
        }

        return tower;
    }
}