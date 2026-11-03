package towerdef.model.entity.tower;

public class BarrackCreator implements TowerCreator{
    @Override
    public Tower create(int x, int y){
        return new Barrack(x, y);
    }
}
