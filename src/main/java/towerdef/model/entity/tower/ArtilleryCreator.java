package towerdef.model.entity.tower;

public class ArtilleryCreator implements TowerCreator{
    @Override
    public Tower create(int x, int y){
        return new Artillery(x,y);
    }
}
