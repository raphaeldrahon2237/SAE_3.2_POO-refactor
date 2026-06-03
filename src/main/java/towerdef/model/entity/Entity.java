package towerdef.model.entity;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class Entity {

    private int id;
    private int hp;
    private int damage;
    private int armor;
    private int attackSpeed;
    private int range;
    private Entity target;
    private double attackTimer;
    private int scoreValue;

    private DoubleProperty x = new SimpleDoubleProperty();
    private DoubleProperty y = new SimpleDoubleProperty();

    public static int nextId = 0;

    public Entity(int hp, int damage, int armor, int atkSpeed, int range, double x, double y) {
        this.id = nextId;
        nextId++;

        this.hp = hp;
        this.damage = damage;
        this.armor = armor;
        this.attackSpeed = atkSpeed;
        this.range = range;

        this.x.set(x);
        this.y.set(y);

        this.attackTimer = 0.0;
    }

    public void takeDamage(int damage) {
        System.out.println(this + " taking damage");
        hp -= damage;
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

    public void setX(double x) {
        this.x.set(x);
    }

    public void setY(double y) {
        this.y.set(y);
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public DoubleProperty yProperty() {
        return y;
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

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public int getRange() {
        return range;
    }

    public void updateAttackTimer(double deltaTime) {
        if (attackTimer > 0.0) {
            attackTimer -= deltaTime;
        }

        if (attackTimer < 0.0) {
            attackTimer = 0.0;
        }
    }

    public boolean canAttack() {
        return attackSpeed > 0 && attackTimer <= 0.0;
    }

    public void resetAttackTimer() {
        if (attackSpeed > 0) {
            attackTimer = 1.0 / attackSpeed;
        }
    }

    public double getAttackTimer() {
        return attackTimer;
    }

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    public String toString() {
        return "" + id;
    }
}