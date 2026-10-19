package towerdef.model.entity.tower;

public enum TowerCharacteristics {

    ARCHER(1, 120, 8, 4, "Rapid fire tower, ideal for eliminating many weaker enemies"),
    ARTILLERY(2, 220, 24, 6, "Big area damage, generalist"),
    BARRACK(3, 180, 0, 0, "Defensive Tower, spawns troops to block the enemies path, little damage"),
    MAGE(4, 170, 14, 5, "Magic damage ignores enemy armor, good at killing strong enemies"),
    EARTHQUAKE(5, 260, 4, 4, "Slows down enemies and deals small continuous damage ");

    private final int typeId;
    private final int cost;
    private final int damage;
    private final int range;
    private final String description;

    TowerCharacteristics(int typeId, int cost, int damage, int range, String description) {
        this.typeId = typeId;
        this.cost = cost;
        this.damage = damage;
        this.range = range;
        this.description = description;
    }

    public int getTypeId() {
        return typeId;
    }

    public int getCost() {
        return cost;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public String getDescription() {
        return description;
    }

    public static TowerCharacteristics fromId(int typeId) {
        for (TowerCharacteristics characteristics : values()) {
            if (characteristics.typeId == typeId) {
                return characteristics;
            }
        }
        return null;
    }
}
