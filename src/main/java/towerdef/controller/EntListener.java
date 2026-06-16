package towerdef.controller;

import javafx.collections.ListChangeListener;
import javafx.scene.layout.Pane;
import towerdef.model.entity.Entity;
import towerdef.model.entity.tower.Tower;
import towerdef.view.EntityView;
import java.util.HashMap;
import java.util.Map;

public class EntListener implements ListChangeListener<Entity> {
    private Pane gamePane;
    private final Map<Entity, EntityView> mapEntityView= new HashMap<>();
    private double animationInterval;
    private double animationTimer;

    public EntListener(Pane gamePane) {
        this.gamePane = gamePane;
        animationInterval=0.3;
        animationTimer=0;
    }

    @Override
    public void onChanged(Change<? extends Entity> change) {
        while (change.next()) {

            if (change.wasAdded()) {
                for (Entity entity : change.getAddedSubList()) {
                    EntityView eV = new     EntityView(gamePane, entity);
                    eV.createEntityView();
                    mapEntityView.put(entity, eV);

                    entity.attackCounterProperty().addListener((obs, oldValue, newValue) -> {
                        if (!oldValue.equals(newValue)) {
                            EntityView view = mapEntityView.get(entity);

                            if (view != null) {
                                view.switchImageAttack();
                            }
                        }
                    });
                }
            }

            if (change.wasRemoved()) {
                for (Entity entity : change.getRemoved()) {
                    if(mapEntityView.containsKey(entity)){
                        mapEntityView.get(entity).removeFromPane();
                        mapEntityView.remove(entity);
                        System.out.println("Removed: " + entity);
                    }

                }
            }
        }
    }

    public void animations(double deltaTime) {
        animationTimer += deltaTime;

        if (animationTimer > animationInterval) {
            for (EntityView entityView : mapEntityView.values()) {
                entityView.switchImageIdle();
            }
        }

        if (animationTimer > animationInterval * 2) {
            for (EntityView entityView : mapEntityView.values()) {
                entityView.switchImageToInitial();
            }

            animationTimer = 0;
        }
    }
}
