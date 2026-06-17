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
import static towerdef.model.map.TileType.TILESIZE;

public class EntityView {

    private Pane gamePane;
    private Entity entity;
    private IntegerProperty level;
    private ImageView iv;
    private Rectangle healthBarBack;
    private Rectangle healthBarFront;

    private static final Image BuildingImg = new Image(EntityView.class.getResourceAsStream("/images/building.png"));

    private static final Image RecruitInitialImg = new Image(EntityView.class.getResourceAsStream("/images/recruit1.png"));
    private static final Image RecruitIdleImg = new Image(EntityView.class.getResourceAsStream("/images/recruit2.png"));
    private static final Image RecruitAttackImg = new Image(EntityView.class.getResourceAsStream("/images/recruit3.png"));

    private static final Image SoldierInitialImg = new Image(EntityView.class.getResourceAsStream("/images/soldierInitial.png"));
    private static final Image SoldierIdleImg = new Image(EntityView.class.getResourceAsStream("/images/soldierIdle.png"));
    private static final Image SoldierAttackImg = new Image(EntityView.class.getResourceAsStream("/images/soldierAttack.png"));


    private static final Image ArcherTower1InitialImg = new Image(EntityView.class.getResourceAsStream("/images/archerTower1Initial.png"));
    private static final Image ArcherTower1AttackImg = new Image(EntityView.class.getResourceAsStream("/images/archerTower1Attack.png"));
    private static final Image ArcherTower2InitialImg = new Image(EntityView.class.getResourceAsStream("/images/archerTower2Initial.png"));
    private static final Image ArcherTower2AttackImg = new Image(EntityView.class.getResourceAsStream("/images/archerTower2Attack.png"));
    private static final Image ArcherTower3InitialImg = new Image(EntityView.class.getResourceAsStream("/images/archerTower3Initial.png"));
    private static final Image ArcherTower3AttackImg = new Image(EntityView.class.getResourceAsStream("/images/archerTower3Attack.png"));

    private static final Image Barrack1InitialImg = new Image(EntityView.class.getResourceAsStream("/images/barrack1.png"));
    private static final Image Barrack1IdleImg = new Image(EntityView.class.getResourceAsStream("/images/barrack2.png"));

    private static final Image Earthquake1InitialImg = new Image(EntityView.class.getResourceAsStream("/images/earthquake1Initial.png"));
    private static final Image Earthquake1AttackImg = new Image(EntityView.class.getResourceAsStream("/images/earthquake1Attack.png"));
    private static final Image Earthquake2InitialImg = new Image(EntityView.class.getResourceAsStream("/images/earthquake2Initial.png"));
    private static final Image Earthquake2AttackImg = new Image(EntityView.class.getResourceAsStream("/images/earthquake2Attack.png"));
    private static final Image Earthquake3InitialImg = new Image(EntityView.class.getResourceAsStream("/images/earthquake3Initial.png"));
    private static final Image Earthquake3AttackImg = new Image(EntityView.class.getResourceAsStream("/images/earthquake3Attack.png"));

    private static final Image ArtilleryTower1InitialImg = new Image(EntityView.class.getResourceAsStream("/images/artilleryTower1.png"));
    private static final Image ArtilleryTower1AttackImg = new Image(EntityView.class.getResourceAsStream("/images/artilleryTower2.png"));

    private static final Image MageTower1InitialImg = new Image(EntityView.class.getResourceAsStream("/images/mageTower1initial.png"));
    private static final Image MageTower1AttackImg = new Image(EntityView.class.getResourceAsStream("/images/mageTower1Attack.png"));
    private static final Image MageTower2InitialImg = new Image(EntityView.class.getResourceAsStream("/images/mageTower2initial.png"));
    private static final Image MageTower2AttackImg = new Image(EntityView.class.getResourceAsStream("/images/mageTower2Attack.png"));
    private static final Image MageTower3InitialImg = new Image(EntityView.class.getResourceAsStream("/images/mageTower3initial.png"));
    private static final Image MageTower3AttackImg = new Image(EntityView.class.getResourceAsStream("/images/mageTower3Attack.png"));


    private static final Image WolfInitialImg = new Image(EntityView.class.getResourceAsStream("/images/wolf1.png"));
    private static final Image WolfIdleImg = new Image(EntityView.class.getResourceAsStream("/images/wolf2.png"));

    private static final Image CatapultInitialImg = new Image(EntityView.class.getResourceAsStream("/images/catapult1.png"));
    private static final Image CatapultIdleImg = new Image(EntityView.class.getResourceAsStream("/images/catapult2.png"));
    private static final Image CatapultAttackImg = new Image(EntityView.class.getResourceAsStream("/images/catapult3.png"));

    private static final Image ShadowSoldierInitialImg = new Image(EntityView.class.getResourceAsStream("/images/shadowSoldier1.png"));
    private static final Image ShadowSoldierIdleImg = new Image(EntityView.class.getResourceAsStream("/images/shadowSoldier2.png"));
    private static final Image ShadowSoldierAttackingImg = new Image(EntityView.class.getResourceAsStream("/images/shadowSoldier3.png"));

    private static final Image ArcherInitialImg = new Image(EntityView.class.getResourceAsStream("/images/archer1.png"));
    private static final Image ArcherIdleImg = new Image(EntityView.class.getResourceAsStream("/images/archer2.png"));
    private static final Image ArcherAttackImg = new Image(EntityView.class.getResourceAsStream("/images/archer3.png"));

    private static final Image ShadowArcherInitialImg = new Image(EntityView.class.getResourceAsStream("/images/shadowArcher1.png"));
    private static final Image ShadowArcherIdleImg = new Image(EntityView.class.getResourceAsStream("/images/shadowArcher2.png"));
    private static final Image ShadowArcherAttackImg = new Image(EntityView.class.getResourceAsStream("/images/shadowArcher3.png"));


    public EntityView(Pane gamePane, Entity entity) {
        this.gamePane = gamePane;
        this.entity = entity;

        iv = new ImageView();
        iv.setPreserveRatio(true);

        healthBarBack = new Rectangle();
        healthBarFront = new Rectangle();
        level= new SimpleIntegerProperty();
    }

    public void createEntityView() {

        switchImageToInitial();


        if(entity instanceof Tower){
            iv.setFitHeight(TILESIZE+10);
            iv.setFitWidth(TILESIZE+10);
        }
        else{
            iv.setFitHeight(TILESIZE+8);
            iv.setFitWidth(TILESIZE+8);
        }
        if(entity.getX()>9) iv.setScaleX(-1);
        iv.xProperty().bind(
                entity.xProperty().multiply(TILESIZE)
        );

        iv.yProperty().bind(
                entity.yProperty().multiply(TILESIZE)
        );
        if(entity instanceof Tower) level.bind(((Tower) entity).levelProperty());
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

    public void switchImageToInitial(){
        if(level.get()==0) iv.setImage(BuildingImg);

        if (entity instanceof ArcherTower) {

            if(level.getValue()==1) iv.setImage(ArcherTower1InitialImg);

            else if(level.getValue()==2) iv.setImage(ArcherTower2InitialImg);

            else if(level.getValue()==3) iv.setImage(ArcherTower3InitialImg);

        }
        else if (entity instanceof Artillery) {
            if (level.getValue()==1) iv.setImage(ArtilleryTower1InitialImg);

            else if (level.getValue()==2) iv.setImage(ArtilleryTower1InitialImg);

        }
        else if (entity instanceof Barrack) {
            if (level.getValue()==1) iv.setImage(Barrack1InitialImg);

            else if (level.getValue()==2) iv.setImage(Barrack1InitialImg);

        }
        else if(entity instanceof Earthquake){
            if (level.getValue()==1) iv.setImage(Earthquake1InitialImg);

            else if (level.getValue()==2) iv.setImage(Earthquake2InitialImg);

            else if(level.getValue()==3) iv.setImage(Earthquake3InitialImg);

        }
        else if(entity instanceof Mage) {
            if (level.getValue()==1) iv.setImage(MageTower1InitialImg);

            else if (level.getValue()==2) iv.setImage(MageTower2InitialImg);

            else if(level.getValue()==3) iv.setImage(MageTower3InitialImg);

        }

        else if (entity instanceof Recruit) iv.setImage(RecruitInitialImg);

        else if (entity instanceof Soldier) iv.setImage(SoldierInitialImg);


        else if (entity instanceof Wolf) iv.setImage(WolfInitialImg);

        else if (entity instanceof Boss) iv.setImage(RecruitInitialImg);

        else if(entity instanceof Catapult) iv.setImage(CatapultInitialImg);

        else if(entity instanceof Archer) iv.setImage(ArcherInitialImg);

        else if (entity instanceof ShadowSoldier) iv.setImage(ShadowSoldierInitialImg);

        else if(entity instanceof ShadowArcher) iv.setImage(ShadowArcherInitialImg);

        else if (entity instanceof  Veteran) iv.setImage(CatapultInitialImg );

    }

    public void switchImageIdle(){


        if(entity instanceof Recruit) iv.setImage(RecruitIdleImg);

        else if(entity instanceof  Soldier) iv.setImage(SoldierIdleImg);


        else if(entity instanceof Barrack) iv.setImage(Barrack1IdleImg);


        else if(entity instanceof Wolf) iv.setImage(WolfIdleImg);

        else if(entity instanceof Catapult)iv.setImage(CatapultIdleImg);

        else if(entity instanceof ShadowSoldier) iv.setImage(ShadowSoldierIdleImg);

        else if(entity instanceof Archer) iv.setImage(ArcherIdleImg);

        else if(entity instanceof ShadowArcher) iv.setImage(ShadowArcherIdleImg);

        else if (entity instanceof  Veteran) iv.setImage(CatapultIdleImg);


    }

    public void switchImageAttack(){

        if(entity instanceof ArcherTower) {
            if (level.getValue() == 1) iv.setImage(ArcherTower1AttackImg);

            else if (level.getValue() == 2) iv.setImage(ArcherTower2AttackImg);

            else if (level.getValue() == 3) iv.setImage(ArcherTower3AttackImg);
        }
        else if(entity instanceof Earthquake) {
            if (level.getValue()==1) iv.setImage(Earthquake1AttackImg);

            else if (level.getValue()==2) iv.setImage(Earthquake2AttackImg);

            else if(level.getValue()==3) iv.setImage(Earthquake3AttackImg);
        }

        else if (entity instanceof Artillery) iv.setImage(ArtilleryTower1AttackImg);

        else if (entity instanceof Mage) {
            if (level.getValue() == 1) iv.setImage(MageTower1AttackImg);

            else if (level.getValue() == 2) iv.setImage(MageTower2AttackImg);

            else if(level.getValue()==3) iv.setImage(MageTower3AttackImg);

        }


        else if (entity instanceof Recruit) iv.setImage(RecruitAttackImg);

        else if (entity instanceof Soldier) iv.setImage(SoldierAttackImg);


        else if (entity instanceof Catapult) iv.setImage(CatapultAttackImg);

        else if (entity instanceof ShadowSoldier) iv.setImage(ShadowSoldierAttackingImg);

        else if(entity instanceof Archer) iv.setImage(ArcherAttackImg);

        else if(entity instanceof ShadowArcher) iv.setImage(ShadowArcherAttackImg);

        else if (entity instanceof  Veteran) iv.setImage(CatapultAttackImg);

    }

}
