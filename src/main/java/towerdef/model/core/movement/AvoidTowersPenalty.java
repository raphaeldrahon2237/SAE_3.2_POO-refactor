package towerdef.model.core.movement;

import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import towerdef.model.entity.tower.Tower;

public class AvoidTowersPenalty implements TilePenaltyStrategy {

    private static final int TOWER_PATH_PENALTY = 30;
    private static final int TOWER_INFLUENCE_RADIUS = 3;

    @Override
    public double getPenalty(Point2D tile, ObservableList<Tower> towers) {
        double penalty = 0;

        for (Tower tower : towers) {
            double distance = Math.abs(tile.getX() - tower.getX())
                    + Math.abs(tile.getY() - tower.getY());

            if (distance <= TOWER_INFLUENCE_RADIUS) {
                penalty += TOWER_PATH_PENALTY;
            }
        }

        return penalty;
    }
}
