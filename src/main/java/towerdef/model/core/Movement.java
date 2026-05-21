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
        if (!path.isEmpty()) {
            // On recupere la premiere case du chemin
            Point2D start = path.get(0);

            troop.setX(start.getX());
            troop.setY(start.getY());

            // On indique que la troupe est actuellement sur la premiere case
            currentTileIndex = 0;
        }
    }

    public Point2D getNextTarget() {
        currentTileIndex++;

        Point2D nextTarget = null;

        // Si l'index est encore dans le chemin, on recupere la prochaine cible
        if (currentTileIndex < path.size()) {
            nextTarget = path.get(currentTileIndex);
        }

        return nextTarget;
    }
}