package towerdef.model.entity;

import towerdef.model.entity.tower.*;

public enum EntityType {

    ARCHERTOWER(1, new ArcherTowerCreator()),
    ARTILLERY(2, new ArtilleryCreator()),
    BARRACK(3, new BarrackCreator()),
    MAGE(4, new MageCreator()),
    EARTHQUAKE(5, new EarthquakeCreator()),
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
