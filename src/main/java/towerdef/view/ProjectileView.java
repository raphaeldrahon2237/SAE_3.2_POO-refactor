package towerdef.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import towerdef.model.entity.Entity;
import towerdef.model.entity.Projectile;
import towerdef.model.entity.tower.ArcherTower;
import towerdef.model.entity.tower.Artillery;
import towerdef.model.entity.tower.Mage;
import towerdef.model.entity.troop.Archer;
import towerdef.model.entity.troop.ShadowArcher;

import static towerdef.model.map.TileType.TILESIZE;

public class ProjectileView {

    private Pane gamePane;
    private Projectile projectile;
    private ImageView iv;

    private final Image arrowImg = new Image(EntityView.class.getResourceAsStream("/images/arrow.png"));
    private final Image magicBoltImg = new Image(EntityView.class.getResourceAsStream("/images/magicBolt.png"));
    private final Image artilleryShotImg= new Image(EntityView.class.getResourceAsStream("/images/artilleryShot.png"));
    private final Image shadowArrowImg= new Image(EntityView.class.getResourceAsStream("/images/shadowArrow.png"));
    private final Image slashImg= new Image(EntityView.class.getResourceAsStream("/images/slash.png"));

    public ProjectileView(Pane gamePane, Projectile projectile) {
        this.gamePane = gamePane;
        this.projectile = projectile;

        iv = new ImageView();
        Entity source = projectile.getSource();
        if(source instanceof ArcherTower || source instanceof Archer) iv.setImage(arrowImg);
        else if(source instanceof Artillery) iv.setImage(artilleryShotImg);
        else if(source instanceof Mage)  iv.setImage(magicBoltImg);
        else if(source instanceof ShadowArcher)  iv.setImage(shadowArrowImg);
        else  iv.setImage(slashImg);

        iv.setFitWidth(TILESIZE/1.5);
        iv.setFitHeight(TILESIZE/1.5);
        iv.setPreserveRatio(true);
    }

    public void createProjectileView() {
        gamePane.getChildren().add(iv);
        iv.xProperty().bind(projectile.xProperty().multiply(TILESIZE).add(TILESIZE / 4.0));
        iv.yProperty().bind(projectile.yProperty().multiply(TILESIZE).add(TILESIZE / 4.0));
    }

    public ImageView getIv() {
        return iv;
    }
}
