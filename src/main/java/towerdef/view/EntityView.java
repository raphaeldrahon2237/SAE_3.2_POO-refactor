package towerdef.view;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import towerdef.model.entity.Entity;
import towerdef.model.entity.tower.ArcherTower;
import towerdef.model.entity.tower.Barrack;
import towerdef.model.entity.troop.Recruit;
import towerdef.model.entity.troop.Wolf;
import static towerdef.model.map.TileType.TILESIZE;

public class EntityView {

    private Pane gamePane;
    private Entity entity;
    private ImageView iv;

    private final Image wolfimg = new Image(EntityView.class.getResourceAsStream("/images/wolf1.png"));
    private final Image recruitimg= new Image(EntityView.class.getResourceAsStream("/images/recruit1.png"));
    private final Image archerTowerimg= new Image(EntityView.class.getResourceAsStream("/images/archerTower1.png"));
    private final Image barrackimg= new Image(EntityView.class.getResourceAsStream("/images/barrack.png"));

    public EntityView(Pane gamePane, Entity entity) {
        this.gamePane = gamePane;
        this.entity = entity;
        iv = new ImageView();
        iv.setPreserveRatio(true);

    }

    public void createEntityView() {

        if (entity instanceof ArcherTower) {
            iv.setImage(archerTowerimg);
        }
        else if (entity instanceof Barrack) {
            iv.setImage(barrackimg);
        }
        else if (entity instanceof Wolf) {
            iv.setImage(wolfimg);
        }
        else if (entity instanceof Recruit) {
            iv.setImage(recruitimg);
        }
        iv.setFitHeight(TILESIZE-5);
        iv.setFitWidth(TILESIZE-5);
        if(entity.getX()>9) iv.setScaleX(-1);
        iv.xProperty().bind(
                entity.xProperty().multiply(TILESIZE)
        );

        iv.yProperty().bind(
                entity.yProperty().multiply(TILESIZE)
        );

        gamePane.getChildren().add(iv);

    }

    public ImageView getIv() {
        return iv;
    }

    public void setImageToNull(){
        this.iv.setImage(null);
    }



}
