package towerdef.model.entity.tower;

public class ArcherTowerCreator implements TowerCreator{
    @Override
    public Tower create(int x, int y){
        return new ArcherTower(x, y);
    }
}
