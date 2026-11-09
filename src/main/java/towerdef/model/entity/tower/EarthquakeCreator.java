package towerdef.model.entity.tower;

public class EarthquakeCreator implements TowerCreator{
    @Override
    public Tower create(int x, int y){
        return new Earthquake(x, y, 1);
    }
}
