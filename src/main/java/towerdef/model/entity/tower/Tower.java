package towerdef.model.entity.tower;

import towerdef.model.entity.AttackType;
import towerdef.model.entity.Entity;

public abstract class Tower extends Entity {

    private int cost;
    private int level;
    private int upgradeCost;

    private int damageUpgrade;
    private int hpUpgrade;
    private int attackSpeedUpgrade;
    private int armorUpgrade;

    public Tower(int hp, int damage, int armor, int attackSpeed, int range, double x, double y, AttackType attackType, int zone, int cost, int level, int upgradeCost, int damageUpgrade, int hpUpgrade, int attackSpeedUpgrade, int armorUpgrade) {
        super(hp, damage, armor, attackSpeed, range, x, y, attackType, zone);
        this.cost = cost;
        this.level = level;
        this.upgradeCost = upgradeCost;
        this.damageUpgrade = damageUpgrade;
        this.hpUpgrade = hpUpgrade;
        this.attackSpeedUpgrade = attackSpeedUpgrade;
        this.armorUpgrade = armorUpgrade;
    }

    public void upgrade() {
        if (level <= 5) {
            level++;
            setHp(getHp() + hpUpgrade);
            setDamage(getDamage() + damageUpgrade);
            setArmor(getArmor() + armorUpgrade);
            setAttackSpeed(attackSpeedUpgrade);
        }
    }

    public int getCost() {
        return cost;
    }
    @Override
    public String toString() {
        return "" + super.toString();
    }

    public int getLevel() {
        return level;
    }


    public int getUpgradeCost() {
        return upgradeCost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public void setUpgradeCost(int upgradeCost) {
        this.upgradeCost = upgradeCost;
    }
}