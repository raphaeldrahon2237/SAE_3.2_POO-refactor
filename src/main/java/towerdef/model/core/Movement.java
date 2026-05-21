package towerdef.model.core;

import javafx.geometry.Point2D;
import towerdef.model.entity.Troop;

import java.util.ArrayList;
import java.util.List;

public class Movement {

    private Troop troop;

    // Chaque Point2D represente une case avec x et y
    private List<Point2D> path;

    private int currentTileIndex;

    public Movement(Troop troop, List<Point2D> path) {
        this.troop = troop;
        this.path = new ArrayList<>(path);
    }

    public void start() {
        if (path.isEmpty()) {
            return;
        }

        // On recupere la premiere case du chemin
        Point2D start = path.get(0);

        // On place la troupe au point de depart
        troop.setX(start.getX());
        troop.setY(start.getY());

        // On indique que la troupe est actuellement sur la premiere case
        currentTileIndex = 0;
    }

    public Point2D getNextTarget() {
        // On passe a la case suivante
        currentTileIndex++;

        // Si l'index depasse la taille du chemin cela veut dire que la troupe est arrivee a la fin
        if (currentTileIndex >= path.size()) {
            return null;
        }

        // On recupere la case cible
        return path.get(currentTileIndex);
    }


}
