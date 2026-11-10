package towerdef.model.entity.tower;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import towerdef.model.Game;
import towerdef.model.entity.AttackType;
import towerdef.model.entity.Entity;
import towerdef.model.entity.impact.ImpactStrategy;
import towerdef.model.entity.upgrade.statsUpgrade;
import towerdef.model.entity.troop.enemy.Enemy;

public abstract class Tower extends Entity {

    public static final int MAX_LEVEL=5;

    private final statsUpgrade statsUpgrade;

    private int towerTypeId;
    private String description;

    private int cost;
    private int sellValue;
    private IntegerProperty level;
    private int upgradeCost;

    private ImpactStrategy impactStrategy;

    public Tower(TowerCharacteristics characteristics, double x, double y, statsUpgrade statsUpgrade) {
        super(characteristics.getHp(), characteristics.getDamage(), characteristics.getArmor(), characteristics.getAttackSpeed(),
                characteristics.getRange(), x, y);
        this.towerTypeId = characteristics.getTypeId();
        this.description = characteristics.getDescription();
        this.cost = characteristics.getCost();
        this.sellValue = cost / 2;
        this.level = new SimpleIntegerProperty(1);
        this.statsUpgrade = statsUpgrade;
        upgradeCost= statsUpgrade.getCost();
    }

    public void upgrade() {
        if (canUpgrade()) {
            level.set(level.get() + 1);
            sellValue += upgradeCost / 2;
            upgradeCost += 0.25 * upgradeCost;
            statsUpgrade.applyUpgradeTo(this);
        }
    }

    public void shootProjectileAtEnemy(Enemy targetEnemy, Game game, AttackType projectileType){
        Point2D targetTile = game.computePreshotTile(this.getX(),this.getY(),targetEnemy);
        this.incrementAttackCounter();
        game.createProjectile(this,targetEnemy, targetTile, projectileType);
    }

    public void killTower(){
        hpProperty().set(0);
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

    public IntegerProperty levelProperty() {
        return level;
    }


    public int getUpgradeCost() {
        return upgradeCost;
    }

    public boolean canUpgrade(){
        return (level.get() < MAX_LEVEL);
    }

    public ImpactStrategy getImpactStrategy() {
        return impactStrategy;
    }

    public void setImpactStrategy(ImpactStrategy impactStrategy) {
        this.impactStrategy = impactStrategy;
    }

    @Override
    public String toString() {
        return "" + super.toString();
    }
}
