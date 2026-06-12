package towerdef.model.entity;

import javafx.beans.property.*;
import towerdef.model.Game;

public abstract class Entity {

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

    public static int nextId = 0;


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

    public void takeDamage(int damage) {
        System.out.println(this + " taking damage");
        int realDamage = Math.max(1, damage - armor);
        hp.set(Math.max(0, hp.get() - realDamage));
    }


    public void incrementAttackCounter() {
        attackCounter.set(attackCounter.get()+1);
    }
    public IntegerProperty attackCounterProperty() {
        return attackCounter;
    }


    public AttackType getAttackType() {
        return attackType;
    }

    public boolean isAlive() {
        return hp.get() > 0;
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

    public int getId() {
        return id;
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

    public int getRange() {
        return range;
    }

    public int getZone() {
        return zone;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setAttackTimer(double attackTimer) {
        this.attackTimer = attackTimer;
    }

    public void setAttackType(AttackType attackType) {
        this.attackType = attackType;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public static void setNextId(int nextId) {
        Entity.nextId = nextId;
    }

    public IntegerProperty hpProperty() {
        return hp;
    }

    public double getMaxHp() {
        return  maxHp;
    }


    public abstract void act(Game game);

}