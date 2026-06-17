package towerdef.model.core;

import javafx.geometry.Point2D;
import towerdef.model.GameEntities;
import towerdef.model.entity.tower.Tower;
import towerdef.model.map.MapGrid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class AStar {

    private static final int TOWER_PATH_PENALTY = 30;
    private static final int TOWER_INFLUENCE_RADIUS = 3;

    private MapGrid mapGrid;
    private Point2D source;
    private Point2D target;
    private GameEntities gameEntities;

    private Map<Point2D, Double> costs;
    private Map<Point2D, Point2D> predecessors;

    public AStar(MapGrid mapGrid, Point2D source, Point2D target, GameEntities gameEntities) {
        this.mapGrid = mapGrid;
        this.source = source;
        this.target = target;
        this.gameEntities = gameEntities;
        costs = new HashMap<>();
        predecessors = new HashMap<>();
        runAStar();
    }

    public ArrayList<Point2D> getPath() {
        ArrayList<Point2D> path = new ArrayList<>();

        if (!source.equals(target) && !predecessors.containsKey(target)) {
            return path;
        }

        Point2D currentTile = target;
        path.add(currentTile);

        while (!currentTile.equals(source)) {
            currentTile = predecessors.get(currentTile);
            path.add(0, currentTile);
        }

        return path;
    }

    public Point2D getSource() {
        return source;
    }

    public void setSource(Point2D source) {
        this.source = source;
        clear();
        runAStar();
    }

    public void setMapGrid(MapGrid mapGrid) {
        this.mapGrid = mapGrid;
        clear();
        runAStar();
    }

    public void setTarget(Point2D target) {
        this.target = target;
        clear();
        runAStar();
    }

    private void runAStar() {
        PriorityQueue<TileCost> queue = new PriorityQueue<>();
        boolean targetFound = false;

        costs.put(source, 0.0);
        queue.add(new TileCost(source, heuristic(source, target)));

        while (!queue.isEmpty() && !targetFound) {
            Point2D currentTile = queue.poll().getTile();

            if (currentTile.equals(target)) {
                targetFound = true;
            } else {
                for (Point2D neighbor : mapGrid.getNeighbors((int) currentTile.getX(), (int) currentTile.getY())) {
                    double newCost = costs.get(currentTile) + movementCost(neighbor);

                    if (!costs.containsKey(neighbor) || newCost < costs.get(neighbor)) {
                        costs.put(neighbor, newCost);
                        predecessors.put(neighbor, currentTile);

                        double priority = newCost + heuristic(neighbor, target);
                        queue.add(new TileCost(neighbor, priority));
                    }
                }
            }
        }
    }

    private double heuristic(Point2D tile, Point2D target) {
        return Math.abs(tile.getX() - target.getX()) + Math.abs(tile.getY() - target.getY());
    }

    private double movementCost(Point2D tile) {
        double cost = 1;

        for (Tower tower : gameEntities.getTowers()) {
            double distance = Math.abs(tile.getX() - tower.getX())
                    + Math.abs(tile.getY() - tower.getY());

            if (distance <= TOWER_INFLUENCE_RADIUS) {
                cost += TOWER_PATH_PENALTY;
            }
        }

        return cost;
    }

    private boolean hasTowerAt(Point2D tile) {
        for (Tower tower : gameEntities.getTowers()) {
            if ((int) tower.getX() == (int) tile.getX() && (int) tower.getY() == (int) tile.getY()) {
                return true;
            }
        }

        return false;
    }

    private void clear() {
        costs.clear();
        predecessors.clear();
    }

    private static class TileCost implements Comparable<TileCost> {

        private Point2D tile;
        private double cost;

        public TileCost(Point2D tile, double cost) {
            this.tile = tile;
            this.cost = cost;
        }

        public Point2D getTile() {
            return tile;
        }

        @Override
        public int compareTo(TileCost otherTileCost) {
            return Double.compare(cost, otherTileCost.cost);
        }
    }
}
