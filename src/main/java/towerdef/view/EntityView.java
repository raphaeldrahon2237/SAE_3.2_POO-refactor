package towerdef.view;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import towerdef.model.entity.Entity;
import towerdef.model.entity.tower.ArcherTower;
import towerdef.model.entity.tower.Artillery;
import towerdef.model.entity.tower.Barrack;
import towerdef.model.entity.tower.Earthquake;
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
    private final Image barrack1img= new Image(EntityView.class.getResourceAsStream("/images/barrack1.png"));
    private final Image barrack2img= new Image(EntityView.class.getResourceAsStream("/images/barrack2.png"));

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
        else if (entity instanceof Artillery) {
            iv.setImage(archerTower2img);
        }
        else if (entity instanceof Barrack) {
            iv.setImage(barrack1img);
        }
        else if(entity instanceof Earthquake){
            iv.setImage(archerTower2img);
        }
        else if (entity instanceof Wolf) {
            iv.setImage(wolf1img);
        }
        else if (entity instanceof Recruit) {
            iv.setImage(recruitimg);
        }
        iv.setFitHeight(TILESIZE+5);
        iv.setFitWidth(TILESIZE+5);
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
        if(entity instanceof Barrack) {
            iv.setImage(barrack2img);
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
        if(entity instanceof Barrack) {
            iv.setImage(barrack1img);
        }
    }

}
