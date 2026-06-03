package towerdef.view;

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
