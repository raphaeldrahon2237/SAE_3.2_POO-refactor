package towerdef.view;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import towerdef.model.entity.Entity;
import towerdef.model.entity.tower.ArcherTower;
import towerdef.model.entity.tower.Barrack;
import towerdef.model.entity.tower.Shockwave;
import towerdef.model.entity.troop.Recruit;
import towerdef.model.entity.troop.Wolf;
import static towerdef.model.map.TileType.TILESIZE;

public class EntityView {

    private Pane gamePane;
    private Entity entity;
    private ImageView iv;


    private final Image wolf1img = new Image(EntityView.class.getResourceAsStream("/images/wolf1.png"));
    private final Image wolf2img = new Image(EntityView.class.getResourceAsStream("/images/wolf2.png"));
    private final Image recruitimg= new Image(EntityView.class.getResourceAsStream("/images/recruit1.png"));
    private final Image archerTower1img= new Image(EntityView.class.getResourceAsStream("/images/archerTower1.png"));
    private final Image archerTower2img= new Image(EntityView.class.getResourceAsStream("/images/archerTower2.png"));
    private final Image shockWaveImg= new Image(EntityView.class.getResourceAsStream("/images/shockwave.png"));
    private final Image barrackimg= new Image(EntityView.class.getResourceAsStream("/images/barrack.png"));

    public EntityView(Pane gamePane, Entity entity) {
        this.gamePane = gamePane;
        this.entity = entity;
        iv = new ImageView();
        iv.setPreserveRatio(true);

    }

    public void createEntityView() {

        if (entity instanceof ArcherTower) {
            iv.setImage(archerTower1img);
        }
        else if (entity instanceof Barrack) {
            iv.setImage(barrackimg);
        }
        else if(entity instanceof Shockwave){
            iv.setImage(shockWaveImg);
        }
        else if (entity instanceof Wolf) {
            iv.setImage(wolf1img);
        }
        else if (entity instanceof Recruit) {
            iv.setImage(recruitimg);
        }
        iv.setFitHeight(TILESIZE);
        iv.setFitWidth(TILESIZE);
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

    public void switchImage(){
        /*
        if(entity instanceof ArcherTower) {
            iv.setImage(archerTower2img);
        }*/
        if(entity instanceof Wolf) {
            iv.setImage(wolf2img);
        }

    }

    public void switchImage2(){
        /*
        if(entity instanceof ArcherTower) {
            iv.setImage(archerTower1img);
        }*/
        if(entity instanceof Wolf) {
            iv.setImage(wolf1img);
        }

    }

}
