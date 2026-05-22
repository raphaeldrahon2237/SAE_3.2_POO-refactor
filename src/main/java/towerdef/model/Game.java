package towerdef.model;

import towerdef.model.entity.Entity;

import java.util.ArrayList;

public class Game {

    private ArrayList<Entity> entities;

    public Game() {
        entities = new ArrayList<>();
    }

    public void removeDeadEntities() {
        for (Entity entity : entities) {
            if (!entity.isAlive()) {
                entities.remove(entity);
            }
        }
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }
    public void updateStates(double deltaTime) {
        //TODO
    }


}
