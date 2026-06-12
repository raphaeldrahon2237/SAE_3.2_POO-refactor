package towerdef.model.entity.tower;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import towerdef.model.entity.AttackType;
import towerdef.model.entity.Entity;

public abstract class Tower extends Entity {

    private int towerTypeId;
    private String description;

    private int cost;
    private int sellValue;
    private IntegerProperty level;
    private int upgradeCost;

    private int damageUpgrade;
    private int hpUpgrade;
    private int attackSpeedUpgrade;
    private int armorUpgrade;

    public Tower(int towerTypeId, String description, int hp, int damage, int armor, int attackSpeed, int range, double x, double y,
                 AttackType attackType, int zone, int cost, int level,
                 int upgradeCost, int damageUpgrade, int hpUpgrade, int attackSpeedUpgrade, int armorUpgrade) {
        super(hp, damage, armor, attackSpeed, range, x, y, attackType, zone);
        this.towerTypeId = towerTypeId;
        this.description = description;
        this.cost = cost;
        this.sellValue = cost / 2;
        this.level = new SimpleIntegerProperty(level);
        this.upgradeCost = upgradeCost;
        this.damageUpgrade = damageUpgrade;
        this.hpUpgrade = hpUpgrade;
        this.attackSpeedUpgrade = attackSpeedUpgrade;
        this.armorUpgrade = armorUpgrade;
    }

    public void upgrade() {
        if (level.get() < 5) {
            level.set(level.get() + 1);
            sellValue += upgradeCost / 2;
            upgradeCost += 0.25 * upgradeCost;
            setHp(getHp() + hpUpgrade);
            setDamage(getDamage() + damageUpgrade);
            setArmor(getArmor() + armorUpgrade);
            setAttackSpeed(getAttackSpeed() + attackSpeedUpgrade);
        }
    }

    public int getTowerTypeId() {
        return towerTypeId;
    }

    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }

    public int getSellValue() {
        return sellValue;
    }

    @Override
    public String toString() {
        return "" + super.toString();
    }

    public IntegerProperty levelProperty() {
        return level;
    }


    public int getUpgradeCost() {
        return upgradeCost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setLevel(int level) {
        this.level.set(level);
    }


    public void setUpgradeCost(int upgradeCost) {
        this.upgradeCost = upgradeCost;
    }
}
