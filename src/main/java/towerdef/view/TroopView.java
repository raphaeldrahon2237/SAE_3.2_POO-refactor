package towerdef.view;

import javafx.scene.layout.Pane;
import towerdef.model.entity.Entity;

import static towerdef.model.map.TileType.TILESIZE;


public class TroopView extends EntityView{

    public TroopView(Pane gamePane, Entity entity){
        super(gamePane, entity);
    }
    @Override
    protected double sizeOfIv() {
        return TILESIZE + 8;
    }

    @Override
    protected SpriteSet currentSprite() {
        return SpriteCollection.get(entity, 1);
    }
}
