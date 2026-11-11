package towerdef.model.core.movement;

import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import towerdef.model.entity.tower.Tower;

public interface TilePenaltyStrategy {

    double getPenalty(Point2D tile, ObservableList<Tower> towers);
}
