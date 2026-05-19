package towerdef.model.entity;

public abstract class Entity {
    private int hp;
    private int damage;
    private int armor;
    private int atkSpeed;
    private int x;
    private int y;

    public Entity(int hp, int damage, int armor, int atkSpeed, int x, int y){
        this.hp=hp;
        this.damage=damage;
        this.armor=armor;
        this.atkSpeed=atkSpeed;
        this.x=x;
        this.y=y;
    }

    public boolean isAlive(){
        return hp > 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveUp(int dy) {
        y -= dy;
    }

    public void moveDown(int dy) {
        y += dy;
    }

    public void moveLeft(int dx) {
        x -= dx;
    }

    public void moveRight(int dx) {
        x += dx;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


}
