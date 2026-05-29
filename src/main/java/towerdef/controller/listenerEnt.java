package towerdef.controller;

import javafx.collections.ListChangeListener;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import towerdef.model.Game;
import towerdef.model.entity.Entity;
import towerdef.view.EntityView;
import java.util.HashMap;
import java.util.Map;

public class listenerEnt implements ListChangeListener<Entity> {
    private Pane gamePane;
    private final Map<Entity, EntityView> mapEntityView= new HashMap<>();
    public listenerEnt(Pane gamePane) {
        this.gamePane = gamePane;
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
}
