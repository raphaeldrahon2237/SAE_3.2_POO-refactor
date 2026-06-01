package towerdef.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import towerdef.model.entity.Entity;
import towerdef.model.entity.Projectile;
import towerdef.model.entity.tower.ArcherTower;
import towerdef.model.entity.tower.Barrack;
import towerdef.model.entity.troop.Recruit;
import towerdef.model.entity.troop.Wolf;

import static towerdef.model.map.TileType.TILESIZE;

public class ProjectileView {

    private Pane gamePane;
    private Projectile projectile;
    private ImageView iv;

    private final Image arrowimg= new Image(EntityView.class.getResourceAsStream("/images/arrow.png"));

    public ProjectileView(Pane gamePane, Projectile projectile) {
        this.gamePane = gamePane;
        this.projectile = projectile;
        iv = new ImageView();
        iv.setPreserveRatio(true);
    }
/*
    public void createProjectileView() {

        iv.setFitHeight(TILESIZE);
        iv.setFitWidth(TILESIZE);
        iv.xProperty().bind(
                projectile.xProperty().multiply(TILESIZE)
        );

        iv.yProperty().bind(
                entity.yProperty().multiply(TILESIZE)
        );

        gamePane.getChildren().add(iv);

    }*/
}
