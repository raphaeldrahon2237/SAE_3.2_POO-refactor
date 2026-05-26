package towerdef.model.entity.tower;

import towerdef.model.entity.Entity;

public abstract class Tower extends Entity {

    public Tower(int hp, int damage, int armor, int atkSpeed, int range, int x, int y) {
        super(hp, damage, armor, atkSpeed, range, x, y);
    }

}
