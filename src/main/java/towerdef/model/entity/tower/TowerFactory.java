package towerdef.model.entity.tower;

import towerdef.model.Game;
import towerdef.model.entity.EntityType;

public class TowerFactory {

    public static Tower createTower(EntityType entityType, int x, int y) {
        if (entityType == null || entityType.getCreator() == null) {
            return null;
        }

        return entityType.getCreator().create(x, y);
    }
}
