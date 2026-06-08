package towerdef.controller;

import javafx.collections.ListChangeListener;
import javafx.scene.layout.Pane;
import towerdef.model.entity.Entity;
import towerdef.view.EntityView;
import java.util.HashMap;
import java.util.Map;

public class EntListener implements ListChangeListener<Entity> {
    private Pane gamePane;
    private final Map<Entity, EntityView> mapEntityView= new HashMap<>();
    private double animationInterval;
    private double animationTimer;
    private int atkCounter;

    public EntListener(Pane gamePane) {
        this.gamePane = gamePane;
        animationInterval=0.2;
        animationTimer=0;
    }

    @Override
    public void onChanged(Change<? extends Entity> change) {
        while (change.next()) {

            if (change.wasAdded()) {
                for (Entity entity : change.getAddedSubList()) {
                    EntityView eV = new EntityView(gamePane, entity);
                    eV.createEntityView();
                    mapEntityView.put(entity, eV);
                }
            }

            if (change.wasRemoved()) {
                for (Entity entity : change.getRemoved()) {
                    if(mapEntityView.containsKey(entity)){
                        mapEntityView.get(entity).setImageToNull();
                        gamePane.getChildren().remove(mapEntityView.get(entity).getIv());
                        mapEntityView.remove(entity);
                        System.out.println("Removed: " + entity);
                    }

                }
            }
        }
    }

    public void animations(double deltaTime){
        animationTimer+=deltaTime;
        if(animationTimer>animationInterval){
            for(Entity e : mapEntityView.keySet()){
                e.attackCounterProperty().addListener((obs, oldV, newV) -> {
                    if(!oldV.equals(newV))
                        mapEntityView.get(e).switchImageAttack();
                    else mapEntityView.get(e).switchImageIdle();
                });
            }
        }
        if(animationTimer>animationInterval*2){
            for(EntityView eV : mapEntityView.values()){
                eV.switchImageToInitial();

            }
            animationTimer=0;
        }

    }
}
