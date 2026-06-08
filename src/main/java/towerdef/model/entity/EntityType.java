package towerdef.model.entity;

public enum EntityType {

    ARCHERTOWER(1),
    ARTILLERY(2),
    BARRACK(3),
    MAGE(4),
    EARTHQUAKE(5),
    RECRUIT(6),
    WOLF(10);


    private final int id;

    EntityType(int id) {
        this.id = id;
    }

    public static EntityType fromId(int id) {
        for (EntityType entityType : values()) {
            if (entityType.id == id) {
                return entityType;
            }
        }
        return null;
    }

}
