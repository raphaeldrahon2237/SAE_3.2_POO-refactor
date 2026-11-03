package towerdef.model.entity.tower;

public class MageCreator implements TowerCreator{
    @Override
    public Tower create(int x, int y){
        return new Mage(x, y);
    }
}
