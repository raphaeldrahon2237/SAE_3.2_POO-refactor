package towerdef.view;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import towerdef.model.entity.Entity;
import towerdef.model.entity.tower.*;
import towerdef.model.entity.troop.*;
import javafx.beans.binding.Bindings;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import static towerdef.model.map.TileType.TILESIZE;

public class EntityView {

    private Pane gamePane;
    private Entity entity;
    private ImageView iv;
    private Rectangle healthBarBack;
    private Rectangle healthBarFront;



    private final Image wolf1img = new Image(EntityView.class.getResourceAsStream("/images/wolf1.png"));
    private final Image wolf2img = new Image(EntityView.class.getResourceAsStream("/images/wolf2.png"));

    private final Image recruit1img= new Image(EntityView.class.getResourceAsStream("/images/recruit1.png"));
    private final Image recruit2img= new Image(EntityView.class.getResourceAsStream("/images/recruit2.png"));

    private final Image archerTower1img= new Image(EntityView.class.getResourceAsStream("/images/archerTower1.png"));
    private final Image archerTower2img= new Image(EntityView.class.getResourceAsStream("/images/archerTower2.png"));

    private final Image barrack1img= new Image(EntityView.class.getResourceAsStream("/images/barrack1.png"));
    private final Image barrack2img= new Image(EntityView.class.getResourceAsStream("/images/barrack2.png"));

    private final Image earthquake1img= new Image(EntityView.class.getResourceAsStream("/images/earthquake1.png"));
    private final Image earthquake2img= new Image(EntityView.class.getResourceAsStream("/images/earthquake2.png"));

    private final Image artilleryTower1img= new Image(EntityView.class.getResourceAsStream("/images/artilleryTower1.png"));
    private final Image artilleryTower2img= new Image(EntityView.class.getResourceAsStream("/images/artilleryTower2.png"));

    private final Image mageTower1img= new Image(EntityView.class.getResourceAsStream("/images/mageTower1.png"));
    private final Image mageTower2img= new Image(EntityView.class.getResourceAsStream("/images/mageTower2.png"));

    public EntityView(Pane gamePane, Entity entity) {
        this.gamePane = gamePane;
        this.entity = entity;

        iv = new ImageView();
        iv.setPreserveRatio(true);

        healthBarBack = new Rectangle();
        healthBarFront = new Rectangle();
    }

    public void createEntityView() {

        if (entity instanceof ArcherTower) {
            iv.setImage(archerTower1img);
        }
        else if (entity instanceof Artillery) {
            iv.setImage(artilleryTower1img);
        }
        else if (entity instanceof Barrack) {
            iv.setImage(barrack1img);
        }
        else if(entity instanceof Earthquake){
            iv.setImage(earthquake1img);
        }
        else if(entity instanceof Mage) {
            iv.setImage(mageTower1img);
        }

        else if (entity instanceof Wolf) {
            iv.setImage(wolf1img);
        }
        else if (entity instanceof Boss){
            iv.setImage(recruit1img);
        }
        else if(entity instanceof Catapult){
            iv.setImage(earthquake2img);
        }
        else if(entity instanceof Archer) {
            iv.setImage(archerTower1img);
        }
        else if (entity instanceof ArmoredSoldier) {
            iv.setImage(wolf1img);

        } else if (entity instanceof Recruit) {
            iv.setImage(recruit1img);
        }
        if(entity instanceof Tower){
            iv.setFitHeight(TILESIZE+8);
            iv.setFitWidth(TILESIZE+8);
        }
        else{
            iv.setFitHeight(TILESIZE+5);
            iv.setFitWidth(TILESIZE+5);
        }
        if(entity.getX()>9) iv.setScaleX(-1);
        iv.xProperty().bind(
                entity.xProperty().multiply(TILESIZE)
        );

        iv.yProperty().bind(
                entity.yProperty().multiply(TILESIZE)
        );
        createHealthBar();

        gamePane.getChildren().addAll(iv, healthBarBack, healthBarFront);

    }
    public void removeFromPane() {
        gamePane.getChildren().removeAll(iv, healthBarBack, healthBarFront);
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

    public ImageView getIv() {
        return iv;
    }

    public void setImageToNull(){
        this.iv.setImage(null);
    }

    public void switchImageToInitial(){
        if(entity instanceof ArcherTower){
            iv.setImage(archerTower1img);
        }
        else if(entity instanceof Earthquake){
            iv.setImage(earthquake1img);
        }
        else if(entity instanceof Artillery){
            iv.setImage(artilleryTower1img);
        }
        else if(entity instanceof Barrack) {
            iv.setImage(barrack1img);
        }
        else if(entity instanceof Mage) {
            iv.setImage(mageTower1img);
        }

        else if(entity instanceof Recruit){
            iv.setImage(recruit1img);
        }
        else if(entity instanceof Wolf) {
            iv.setImage(wolf1img);
        }

    }

    public void switchImageIdle(){

        if(entity instanceof Wolf) {
            iv.setImage(wolf2img);
        }
        else if(entity instanceof Recruit) {
            iv.setImage(recruit2img);
        }
        else if(entity instanceof Barrack) {
            iv.setImage(barrack2img);
        }

    }

    public void switchImageAttack(){
        if(entity instanceof ArcherTower) {
            iv.setImage(archerTower2img);
        }
        else if(entity instanceof Earthquake) {
            iv.setImage(earthquake2img);
        }
        else if (entity instanceof Artillery) {
            iv.setImage(artilleryTower2img);
        }
        else if (entity instanceof Mage) {
            iv.setImage(mageTower2img);
        }
    }

}
