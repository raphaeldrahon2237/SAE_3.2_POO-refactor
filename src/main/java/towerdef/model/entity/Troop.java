package towerdef.model.entity;

import towerdef.model.map.Position;

public abstract class Troop extends Entity {
    private int mvSpeed;
    private Position pos;


    public Troop(int hp, int damage, int armor, int atkSpeed, int x, int y){
        super(hp, damage, armor, atkSpeed);
        this.pos = new Position(x, y);
    }

    public Position getPos() {
        return pos;
    }
}
