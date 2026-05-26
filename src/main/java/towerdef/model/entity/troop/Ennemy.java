package towerdef.model.entity.troop;

public abstract class Ennemy extends Troop {

    private int targetMode;

    public Ennemy(int x, int y, int targetMode) {
        super(3, 2, 0, 5, 1, 6, x, y);
        this.targetMode = targetMode;
    }


}
