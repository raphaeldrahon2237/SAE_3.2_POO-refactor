package towerdef.model.entity.troop;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends Troop {

    private List<Point2D> path;
    private int currentTileIndex;
    private Point2D currentTarget;

    public Enemy(int hp, int damage, int armor, int atkSpeed, int range, int mvSpeed, int x, int y) {
        super(hp, damage, armor, atkSpeed, range, mvSpeed, x, y);
        this.path = new ArrayList<>();
        this.currentTileIndex = 0;
        this.currentTarget = null;
    }

    public void setPath(List<Point2D> path) {
        this.path = new ArrayList<>(path);
    }

    public void startMovement() {
        if (!path.isEmpty()) {
            Point2D start = path.get(0);

            setX(start.getX());
            setY(start.getY());

            currentTileIndex = 0;
            currentTarget = null;
        }
    }

    public void updateMovement(double deltaTime) {
        if (currentTarget == null) {
            currentTarget = getNextTarget();
        }

        if (currentTarget == null) {
            return;
        }

        double currentX = getX();
        double currentY = getY();

        double targetX = currentTarget.getX();
        double targetY = currentTarget.getY();

        double directionX = targetX - currentX;
        double directionY = targetY - currentY;

        double distance = Math.sqrt(directionX * directionX + directionY * directionY);

        if (distance == 0) {
            currentTarget = null;
            return;
        }

        double movementDistance = getMooveSpeed() * deltaTime;

        if (movementDistance >= distance) {
            setX(targetX);
            setY(targetY);
            currentTarget = null;
            return;
        }

        double normalizedX = directionX / distance;
        double normalizedY = directionY / distance;

        setX(currentX + normalizedX * movementDistance);
        setY(currentY + normalizedY * movementDistance);
    }


    private Point2D getNextTarget() {
        currentTileIndex++;

        if (currentTileIndex < path.size()) {
            return path.get(currentTileIndex);
        }

        return null;
    }
}