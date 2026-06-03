package towerdef.controller;

import javafx.collections.ListChangeListener;
import javafx.scene.layout.Pane;
import towerdef.model.entity.Projectile;
import towerdef.view.ProjectileView;

import java.util.HashMap;
import java.util.Map;

public class ProjectileListener implements ListChangeListener<Projectile> {

    private Pane gamePane;
    private Map<Projectile, ProjectileView> projectileViews = new HashMap<>();

    public ProjectileListener(Pane gamePane) {
        this.gamePane = gamePane;
    }

    @Override
    public void onChanged(Change<? extends Projectile> change) {
        while (change.next()) {

            if (change.wasAdded()) {
                for (Projectile projectile : change.getAddedSubList()) {
                    ProjectileView projectileView = new ProjectileView(gamePane, projectile);
                    projectileView.createProjectileView();
                    projectileViews.put(projectile, projectileView);
                }
            }

            if (change.wasRemoved()) {
                for (Projectile projectile : change.getRemoved()) {
                    ProjectileView projectileView = projectileViews.get(projectile);

                    if (projectileView != null) {
                        gamePane.getChildren().remove(projectileView.getIv());
                        projectileViews.remove(projectile);
                    }
                }
            }
        }
    }

}