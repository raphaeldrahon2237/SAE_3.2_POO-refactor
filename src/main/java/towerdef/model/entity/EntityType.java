package towerdef.model.entity;

import towerdef.model.entity.tower.*;

public enum EntityType {

    ARCHERTOWER(1, (x,y) -> new ArcherTower(x,y)),
    ARTILLERY(2, (x,y) -> new Artillery(x, y)),
    BARRACK(3, (x,y) -> new Barrack(x, y)),
    MAGE(4, (x,y) -> new Mage(x, y)),
    EARTHQUAKE(5, (x,y) -> new Earthquake(x, y)),
    RECRUIT(6, null ),
    WOLF(10, null),
    BOSS(11, null);


    private final int id;
    private final TowerCreator creator;

    EntityType(int id, TowerCreator creator) {
        this.id = id;
        this.creator = creator;
    }

    public static EntityType fromId(int id) {
        for (EntityType entityType : values()) {
            if (entityType.id == id) {
                return entityType;
            }
        }
        return null;
    }

    public TowerCreator getCreator(){
        return this.creator;
    }

}
