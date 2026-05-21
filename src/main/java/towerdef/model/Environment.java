package towerdef.model;

import towerdef.model.entity.Entity;

import java.util.ArrayList;

public class Environment {

    private ArrayList<Entity> entities;

    public Environment() {
        entities = new ArrayList<>();
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public  ArrayList<Entity> getEntities() {
        return entities;
    }
}
