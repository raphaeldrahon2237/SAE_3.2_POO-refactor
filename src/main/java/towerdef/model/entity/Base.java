package towerdef.model.entity;

public class Base {
    private int x;
    private int y;
    private int hp;

    public Base(int x, int y, int hp) {
        this.x = x;
        this.y = y;
        this.hp = hp;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getHp() {
        return hp;
    }
}
