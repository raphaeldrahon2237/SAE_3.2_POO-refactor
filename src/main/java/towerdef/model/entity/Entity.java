package towerdef.model.entity;

import javafx.beans.property.*;
import towerdef.model.Game;

public abstract class Entity {

    public static int nextId = 0;

    private int id;
    private IntegerProperty hp = new SimpleIntegerProperty();
    private int maxHp;
    private int damage;
    private int armor;
    private int attackSpeed;
    private int range;
    private Entity target;
    private double attackTimer;
    private AttackType attackType;
    private int zone;

    //private Game game;

    private DoubleProperty x = new SimpleDoubleProperty();
    private DoubleProperty y = new SimpleDoubleProperty();
    private IntegerProperty attackCounter = new SimpleIntegerProperty();

    public Entity(int hp, int damage, int armor, int attackSpeed, int range, double x, double y) {
        this(hp, damage, armor, attackSpeed, range, x, y, AttackType.NORMAL, 0);
    }

    public Entity(int hp, int damage, int armor, int attackSpeed, int range, double x, double y, AttackType attackType, int zone) {
        this.id = nextId;
        nextId++;

        this.hp.set(hp);
        this.maxHp = hp;
        this.damage = damage;
        this.armor = armor;
        this.attackSpeed = attackSpeed;
        this.range = range;

        this.x.set(x);
        this.y.set(y);
        this.attackCounter.set(0);

        this.attackTimer = 0.0;
        this.attackType = attackType;
        this.zone = zone;

        //this.game= game;
    }

    public abstract void attack(Game game);

    public void takeDamage(int damage) {
        System.out.println(this + " taking damage");
        int realDamage = Math.max(1, damage - armor);
        hp.set(Math.max(0, hp.get() - realDamage));
    }

    public void takeMagicDamage(int damage) {
        System.out.println(this + " taking magic damage");
        hp.set(Math.max(0, hp.get() - damage));
    }

    public boolean isAlive() {
        return hp.get() > 0;
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

    public void incrementAttackCounter() {
        attackCounter.set(attackCounter.get()+1);
    }
    public IntegerProperty attackCounterProperty() {
        return attackCounter;
    }

    public int getId() {
        return id;
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
        return hp.get();
    }

    public void setHp(int hp) {
        this.hp.set(hp);

        if (hp > maxHp) {
            maxHp = hp;
        }
    }

    public IntegerProperty hpProperty() {
        return hp;
    }

    public double getMaxHp() {
        return  maxHp;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public int getRange() {
        return range;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public int getZone() {
        return zone;
    }

    public String toString() {
        return "" + id;
    }

}
