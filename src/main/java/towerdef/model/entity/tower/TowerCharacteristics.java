package towerdef.model.entity.tower;

import towerdef.model.entity.AttackType;
import towerdef.model.entity.impact.AreaImpact;
import towerdef.model.entity.impact.ImpactStrategy;
import towerdef.model.entity.impact.MagicImpact;
import towerdef.model.entity.impact.NormalImpact;

public enum TowerCharacteristics {

    ARCHER(1, 120, 8, 4, 35, 2, 3, new NormalImpact(), "Rapid fire tower, ideal for eliminating many weaker enemies"),
    ARTILLERY(2, 220, 24, 6, 45, 2, 1,  new AreaImpact(), "Big area damage, generalist"),
    BARRACK(3, 180, 0, 0, 80, 8, 0,   new NormalImpact(), "Defensive Tower, spawns troops to block the enemies path, little damage"),
    MAGE(4, 170, 14, 5, 35, 1, 2,  new MagicImpact(), "Magic damage ignores enemy armor, good at killing strong enemies"),
    EARTHQUAKE(5, 260, 4, 4, 60, 3, 1,   new NormalImpact(), "Slows down enemies and deals small continuous damage ");

    private final int typeId;
    private final int cost;
    private final int damage;
    private final int range;
    private final int hp;
    private final int armor;
    private final int attackSpeed;
    ImpactStrategy impactStrategy;
    private final String description;

    TowerCharacteristics(int typeId, int cost, int damage, int range, int hp, int armor, int attackSpeed, ImpactStrategy impactStrategy, String description) {
        this.typeId = typeId;
        this.cost = cost;
        this.damage = damage;
        this.range = range;
        this.hp = hp;
        this.armor = armor;
        this.attackSpeed = attackSpeed;
        this.impactStrategy= impactStrategy;
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

    public ImpactStrategy getImpactStrategy() {
        return impactStrategy;
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
