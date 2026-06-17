package towerdef.model.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Base {
    private int x;
    private int y;
    private IntegerProperty hp;
    private int maxHp;

    public Base(int x, int y, int hp) {
        this.x = x;
        this.y = y;
        this.hp = new SimpleIntegerProperty(hp);
        this.maxHp = hp;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHp() {
        return  hp.get();
    }

    public int getMaxHp() {
        return maxHp;
    }

    public IntegerProperty hpProperty(){
        return hp;
    }

    public void takeDamage(int damage) {
        hp.set(Math.max(0, hp.get() - damage));
    }

    public boolean isDestroyed() {
        return hp.get() <= 0;
    }
}
