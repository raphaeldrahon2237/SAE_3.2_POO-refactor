package towerdef.model.entity;

public abstract class Entity {
    private int hp;
    private int damage;
    private int armor;
    private int atkSpeed;

    public Entity(int hp, int damage, int armor, int atkSpeed){
        this.hp=hp;
        this.damage=damage;
        this.armor=armor;
        this.atkSpeed=atkSpeed;
    }

    public boolean isAlive(){return hp<0;}


}
