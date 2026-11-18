package towerdef.view;


import javafx.scene.layout.Pane;
import towerdef.model.entity.tower.Tower;

import static towerdef.model.map.TileType.TILESIZE;

public class TowerView extends EntityView {
    private final Tower tower;

    public TowerView(Pane gamePane, Tower tower) {
        super(gamePane, tower);
        this.tower = tower;
    }

    @Override
    protected double sizeOfIv() {
        return TILESIZE+10;
    }

    @Override
    protected SpriteSet currentSprite() {
        return SpriteCollection.get(tower, tower.levelProperty().get());
    }

    @Override
    protected void onCreate(){
        switchImageToInitial();

        tower.levelProperty().addListener((obs, oldLevel, newLevel) -> switchImageToInitial());
    }
}
