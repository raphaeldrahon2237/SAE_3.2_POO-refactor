package towerdef.controller;

import javafx.collections.ListChangeListener;
import javafx.scene.layout.Pane;
import towerdef.model.entity.Entity;
import towerdef.view.EntityView;

public class listenerEnt implements ListChangeListener<Entity> {
    private Pane gamePane;

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
                }
            }

            if (change.wasRemoved()) {
                for (Entity entity : change.getRemoved()) {
                    System.out.println("Removed: " + entity);
                }
            }
        }
    }
}
