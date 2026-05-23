package towerdef.model.core;

import javafx.geometry.Point2D;
import towerdef.model.entity.Troop;

import java.util.ArrayList;
import java.util.List;

public class Movement {

    private Troop troop;
    private List<Point2D> path;
    private int currentTileIndex;
    private Point2D currentTarget;

    public Movement(Troop troop, List<Point2D> path) {
        this.troop = troop;
        this.path = new ArrayList<>(path);
    }

    public void start() {
        if (!path.isEmpty()) {
            Point2D start = path.get(0);

            // On place la troupe au point de depart
            troop.setX(start.getX());
            troop.setY(start.getY());

            currentTileIndex = 0;
        }
    }

    public Point2D getNextTarget() {
        // On passe a la case suivante
        currentTileIndex++;

        Point2D nextTarget = null;

        // Si l'index est encore dans le chemin, on recupere la prochaine cible
        if (currentTileIndex < path.size()) {
            nextTarget = path.get(currentTileIndex);
        }

        return nextTarget;
    }

    public void update(double deltaTime) {
        if (currentTarget == null) {
            currentTarget = getNextTarget();
        }

        if (currentTarget == null) {
            return;
        }

        double currentX = troop.getX();
        double currentY = troop.getY();

        double targetX = currentTarget.getX();
        double targetY = currentTarget.getY();

        double directionX = targetX - currentX;
        double directionY = targetY - currentY;

        double distance = Math.sqrt(directionX * directionX + directionY * directionY);

        if (distance == 0) {
            currentTarget = null;
            return;
        }

        double movementDistance = troop.getMvSpeed() * deltaTime;

        if (movementDistance >= distance) {
            troop.setX(targetX);
            troop.setY(targetY);
            currentTarget = null;
            return;
        }

        double normalizedX = directionX / distance;
        double normalizedY = directionY / distance;

        troop.setX(currentX + normalizedX * movementDistance);
        troop.setY(currentY + normalizedY * movementDistance);
    }
}
