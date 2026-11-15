package towerdef.model.entity.tower;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import towerdef.model.Game;
import towerdef.model.entity.AttackType;
import towerdef.model.entity.Entity;
import towerdef.model.entity.impact.ImpactStrategy;
import towerdef.model.entity.upgrade.StatsUpgrade;
import towerdef.model.entity.troop.enemy.Enemy;
import towerdef.model.entity.upgrade.Upgrade;

import java.util.ArrayList;
import java.util.List;

public abstract class Tower extends Entity {


    private final List<Upgrade> upgradePath;

    private int towerTypeId;
    private String description;

    private int cost;
    private int sellValue;
    private IntegerProperty level;
    private int upgradeCost;

    private ImpactStrategy impactStrategy;

    public Tower(TowerCharacteristics characteristics, double x, double y, List<Upgrade> upgradePath, int upgradeCost){
        super(characteristics.getHp(), characteristics.getDamage(), characteristics.getArmor(), characteristics.getAttackSpeed(),
                characteristics.getRange(), x, y);
        this.towerTypeId = characteristics.getTypeId();
        this.description = characteristics.getDescription();
        this.cost = characteristics.getCost();
        this.sellValue = cost / 2;
        this.level = new SimpleIntegerProperty(1);
        this.upgradePath= upgradePath;
        this.upgradeCost= upgradeCost;
        this.impactStrategy= characteristics.getImpactStrategy();
    }

    public void upgrade() {
        if (canUpgrade()) {
            int lvl= levelProperty().get();
            upgradePath.get(lvl-1).applyUpgradeTo(this);
            level.set(level.get() + 1);
            sellValue += upgradeCost / 2;
            upgradeCost += 0.25 * upgradeCost;

        }
    }

    public void shootProjectileAtEnemy(Enemy targetEnemy, Game game, ImpactStrategy impactStrategy){
        Point2D targetTile = game.computePreshotTile(this.getX(),this.getY(),targetEnemy);
        this.incrementAttackCounter();
        game.createProjectile(this,targetEnemy, targetTile, impactStrategy);
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
        return (level.get() < upgradePath.size()+1);
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
