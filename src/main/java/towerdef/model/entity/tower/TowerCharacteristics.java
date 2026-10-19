package towerdef.model.entity.tower;

import towerdef.model.entity.AttackType;

public enum TowerCharacteristics {

    ARCHER(1, 120, 8, 4, 35, 2, 3, AttackType.NORMAL, 0, 1, "Rapid fire tower, ideal for eliminating many weaker enemies"),
    ARTILLERY(2, 220, 24, 6, 45, 2, 1, AttackType.AOE, 2, 1, "Big area damage, generalist"),
    BARRACK(3, 180, 0, 0, 80, 8, 0, AttackType.NORMAL, 0, 1, "Defensive Tower, spawns troops to block the enemies path, little damage"),
    MAGE(4, 170, 14, 5, 35, 1, 2, AttackType.MAGIC, 1, 1, "Magic damage ignores enemy armor, good at killing strong enemies"),
    EARTHQUAKE(5, 260, 4, 4, 60, 3, 1, AttackType.EARTHQUAKE, 0, 1, "Slows down enemies and deals small continuous damage ");

    private final int typeId;
    private final int cost;
    private final int damage;
    private final int range;
    private final int hp;
    private final int armor;
    private final int attackSpeed;
    private final AttackType attackType;
    private final int zone;
    private final int level;
    private final String description;

    TowerCharacteristics(int typeId, int cost, int damage, int range, int hp, int armor, int attackSpeed,
                         AttackType attackType, int zone, int level, String description) {
        this.typeId = typeId;
        this.cost = cost;
        this.damage = damage;
        this.range = range;
        this.hp = hp;
        this.armor = armor;
        this.attackSpeed = attackSpeed;
        this.attackType = attackType;
        this.zone = zone;
        this.level = level;
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

    public int getHp() {
        return hp;
    }

    public int getArmor() {
        return armor;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public int getZone() {
        return zone;
    }

    public int getLevel() {
        return level;
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
