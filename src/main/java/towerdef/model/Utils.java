package towerdef.model;

import javafx.geometry.Point2D;

public final class Utils {

    private Utils() {
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;

        return Math.sqrt(dx * dx + dy * dy);
    }

    public static boolean isInRange(double sourceX, double sourceY, double targetX, double targetY, int range) {
        return distance(sourceX, sourceY, targetX, targetY) <= range;
    }

    public static Point2D toTilePosition(double x, double y) {
        return new Point2D(Math.round(x), Math.round(y));
    }
}
