package towerdef.model.core.movement;

import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import towerdef.model.entity.tower.Tower;

public class NoPenalty implements TilePenaltyStrategy {

    @Override
    public double getPenalty(Point2D tile, ObservableList<Tower> towers) {
        return 0;
    }
}
