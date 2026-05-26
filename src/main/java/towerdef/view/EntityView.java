package towerdef.view;
import javafx.scene.Node;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import towerdef.model.entity.Entity;
import towerdef.model.entity.EntityType;
import towerdef.model.entity.tower.ArcherTower;
import towerdef.model.entity.troop.Recruit;
import towerdef.model.entity.troop.Wolf;

import static towerdef.model.map.TileType.TILESIZE;

public class EntityView {

    private Pane gamePane;
    private Entity entity;
    private Circle circle;
    private ImageView iv;

    Image wolfimg = new Image(EntityView.class.getResourceAsStream("/images/wolf.png"));
    Image recruitimg= new Image(EntityView.class.getResourceAsStream("/images/recruit.png"));

    public EntityView(Pane gamePane, Entity entity) {
        this.gamePane = gamePane;
        this.entity = entity;
        this.circle = new Circle();
        circle.setFill(Color.PURPLE);
        iv = new ImageView();
    }

    public void createEntityView() {
        Circle circle = new Circle();


        if (entity instanceof ArcherTower) {
            iv.setImage(wolfimg);
        }
        else if (entity instanceof Wolf) {
            iv.setImage(wolfimg);
        }
        else if (entity instanceof Recruit) {
            iv.setImage(recruitimg);
        }

        iv.xProperty().bind(
                entity.xProperty().multiply(TILESIZE).add(iv.getFitWidth()/2)
        );

        iv.yProperty().bind(
                entity.yProperty().multiply(TILESIZE).add(iv.getFitHeight()/2)
        );

        gamePane.getChildren().add(iv);

    }

}
