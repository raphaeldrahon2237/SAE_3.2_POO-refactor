package towerdef.model.entity;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class Entity {
    private int id;
    private int hp;
    private int damage;
    private int armor;
    private int atkSpeed;
    private int range;

    private  DoubleProperty x = new SimpleDoubleProperty();
    private  DoubleProperty y = new SimpleDoubleProperty();

    public static int nextId=0;

    public Entity(int hp, int damage, int armor, int atkSpeed, int range, double x, double y) {
        this.id=nextId;
        nextId++;
        this.hp = hp;
        this.damage = damage;
        this.armor = armor;
        this.atkSpeed = atkSpeed;
        this.x.set(x);
        this.y.set(y);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public double getX() {
        return x.get();
    }

    public double getY() {
        return y.get();
    }

    public DoubleProperty xProperty() {
        return x;
    }

    //modif
    public void takeDamage(int damage) {
        hp -= damage-armor;
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public void setY(double y) {
        this.y.set(y);
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDamage() {
        return damage;
    }

    public int getArmor() {
        return armor;
    }

    public int getAtkSpeed() {
        return atkSpeed;
    }

    public int getRange() {return range;}
}