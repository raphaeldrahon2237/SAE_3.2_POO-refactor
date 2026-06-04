package towerdef.model.entity.tower;

import towerdef.model.entity.AttackType;
import towerdef.model.entity.Entity;

public abstract class Tower extends Entity {

    private int cost;

    public Tower(int hp, int damage, int armor, int atkSpeed, int range, int x, int y, int cost) {
        super(hp, damage, armor, atkSpeed, range, x, y);
        this.cost = cost;
    }

    public Tower(int hp, int damage, int armor, int atkSpeed, int range, int x, int y, int cost, AttackType attackType, int zone) {
        super(hp, damage, armor, atkSpeed, range, x, y, attackType, zone);
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }
    @Override
    public String toString() {
        return ""+super.toString();
    }
}