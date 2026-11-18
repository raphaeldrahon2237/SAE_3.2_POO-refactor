package towerdef.view;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import towerdef.model.entity.Entity;
import towerdef.model.entity.tower.*;
import towerdef.model.entity.troop.*;
import javafx.beans.binding.Bindings;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import towerdef.model.entity.troop.enemy.*;

import static towerdef.model.map.TileType.TILESIZE;

public  abstract class EntityView {

    private Pane gamePane;
    protected Entity entity;
    protected ImageView iv;
    private Rectangle healthBarBack;
    private Rectangle healthBarFront;

    protected EntityView(Pane gamePane, Entity entity) {
        this.gamePane = gamePane;
        this.entity = entity;
        iv= new ImageView();
        iv.setPreserveRatio(true);
        healthBarBack = new Rectangle();
        healthBarFront = new Rectangle();

    }

    public final void createEntityView() {
        iv.setFitWidth(sizeOfIv());
        iv.setFitHeight(sizeOfIv());
        if(entity.getX()>9) iv.setScaleX(-1);
        iv.xProperty().bind(entity.xProperty().multiply(TILESIZE));
        iv.yProperty().bind(entity.yProperty().multiply(TILESIZE));
        createHealthBar();
        onCreate();

        gamePane.getChildren().addAll(iv, healthBarBack, healthBarFront);
    }

    protected abstract double sizeOfIv();
    protected abstract SpriteSet currentSprite();
    public void removeFromPane() {
        gamePane.getChildren().removeAll(iv, healthBarBack, healthBarFront);
    }

    protected void onCreate(){
        switchImageToInitial();
    }

    public void switchImageToInitial(){
        iv.setImage(currentSprite().initial());
    }

    public void switchImageIdle(){
        iv.setImage(currentSprite().idle());
    }

    public void switchImageAttack(){
        iv.setImage(currentSprite().attack());
    }

    private void createHealthBar() {
        double barWidth = TILESIZE;
        double barHeight = 5;

        healthBarBack.setWidth(barWidth);
        healthBarBack.setHeight(barHeight);
        healthBarBack.setFill(Color.DARKRED);

        healthBarFront.setHeight(barHeight);
        healthBarFront.setFill(Color.LIMEGREEN);

        healthBarBack.xProperty().bind(
                entity.xProperty().multiply(TILESIZE).add(2)
        );

        healthBarBack.yProperty().bind(
                entity.yProperty().multiply(TILESIZE).subtract(7)
        );

        healthBarFront.xProperty().bind(
                healthBarBack.xProperty()
        );

        healthBarFront.yProperty().bind(
                healthBarBack.yProperty()
        );

        healthBarFront.widthProperty().bind(
                Bindings.createDoubleBinding(
                        () -> {
                            double ratio = (double) entity.getHp() / entity.getMaxHp();
                            ratio = Math.max(0, Math.min(1, ratio));
                            return barWidth * ratio;
                        },
                        entity.hpProperty()
                )
        );
    }


}
