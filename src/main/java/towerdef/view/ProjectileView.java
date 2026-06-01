package towerdef.view;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import towerdef.model.entity.Projectile;

import static towerdef.model.map.TileType.TILESIZE;

public class ProjectileView {

    private Pane gamePane;
    private Projectile projectile;
    private ImageView iv;

    private final Image arrowImg = new Image(
            EntityView.class.getResourceAsStream("/images/arrow.png")
    );

    public ProjectileView(Pane gamePane, Projectile projectile) {
        this.gamePane = gamePane;
        this.projectile = projectile;

        iv = new ImageView();
        iv.setImage(arrowImg);
        iv.setFitWidth(TILESIZE / 2.0);
        iv.setFitHeight(TILESIZE / 2.0);
        iv.setPreserveRatio(true);
    }

    public void createProjectileView() {
        update();
        gamePane.getChildren().add(iv);
    }

    public void update() {
        Point2D current = projectile.getCurrentTile();
        Point2D next = projectile.getNextTile();
        double progress = projectile.getProgressToNextTile();

        double x = current.getX() + (next.getX() - current.getX()) * progress;
        double y = current.getY() + (next.getY() - current.getY()) * progress;

        iv.setLayoutX(x * TILESIZE + TILESIZE / 4.0);
        iv.setLayoutY(y * TILESIZE + TILESIZE / 4.0);
    }

    public ImageView getIv() {
        return iv;
    }
}