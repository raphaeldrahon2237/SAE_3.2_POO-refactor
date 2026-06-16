package towerdef.model.map;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class MapGrid {

    private static final int TARGET_WIDTH = 40;
    private static final int TARGET_HEIGHT = 26;
    private static final int DEFAULT_TILE = 1;

    private int[][] map;

    public MapGrid() {
        int[][] mapRows = new int[][]
                {
                        {3, 3, 3, 3, 3, 3, 3, 3, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 1, 15, 15, 15, 15, 16, 15, 15, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {3, 3, 3, 3, 3, 3, 3, 3, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 1, 1, 15, 15, 16, 16, 16, 15, 15, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {7, 7, 7, 7, 7, 7, 7, 7, 12, 13, 7, 7, 7, 7, 7, 7, 7, 7, 14, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 10, 3, 1, 1, 1, 1, 1, 1, 1},
                        {3, 3, 1, 3, 3, 3, 3, 3, 3, 6, 1, 1, 1, 1, 1, 1, 1, 1, 6, 1, 3, 1, 15, 16, 16, 16, 15, 16, 15, 1, 3, 6, 1, 1, 1, 1, 1, 1, 1, 1},
                        {3, 3, 3, 3, 3, 3, 3, 3, 3, 6, 1, 3, 3, 1, 1, 1, 1, 1, 6, 1, 1, 3, 1, 15, 16, 16, 16, 15, 16, 15, 1, 6, 1, 1, 1, 1, 1, 1, 1, 1},
                        {3, 3, 3, 1, 3, 3, 3, 3, 3, 9, 10, 3, 3, 1, 1, 1, 1, 1, 9, 7, 7, 13, 7, 7, 7, 7, 10, 16, 16, 15, 1, 6, 1, 1, 1, 1, 1, 1, 1, 1},
                        {2, 3, 3, 3, 1, 3, 3, 3, 1, 3, 6, 1, 3, 3, 1, 1, 1, 1, 1, 1, 1, 6, 1, 1, 1, 1, 6, 15, 16, 15, 3, 6, 3, 1, 1, 1, 1, 1, 1, 1},
                        {2, 2, 3, 3, 3, 1, 3, 3, 1, 1, 6, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 6, 1, 1, 1, 1, 6, 15, 15, 3, 1, 6, 1, 1, 1, 1, 1, 1, 1, 1},
                        {2, 2, 2, 11, 7, 7, 7, 13, 7, 7, 12, 7, 13, 7, 7, 7, 13, 7, 7, 7, 7, 8, 1, 1, 1, 1, 9, 13, 7, 7, 7, 14, 7, 7, 7, 7, 7, 7, 7, 7},
                        {2, 11, 7, 8, 3, 3, 3, 6, 1, 1, 1, 1, 6, 1, 1, 1, 6, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 6, 1, 1, 1, 6, 1, 1, 1, 1, 1, 1, 1, 1},
                        {7, 8, 2, 2, 2, 3, 3, 6, 1, 1, 1, 11, 8, 1, 1, 1, 6, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 6, 1, 1, 1, 6, 1, 1, 1, 1, 1, 1, 1, 1},
                        {2, 2, 2, 2, 2, 2, 3, 6, 1, 1, 1, 6, 1, 1, 1, 1, 6, 1, 1, 1, 1, 3, 1, 1, 1, 3, 1, 6, 1, 1, 1, 6, 1, 1, 1, 1, 1, 1, 1, 1},
                        {2, 2, 2, 2, 2, 2, 11, 8, 1, 1, 11, 12, 7, 7, 7, 7, 4, 13, 7, 7, 7, 13, 7, 7, 7, 7, 7, 8, 1, 1, 1, 6, 1, 1, 1, 1, 1, 1, 1, 1},
                        {2, 2, 2, 2, 2, 2, 6, 3, 3, 1, 6, 1, 1, 1, 1, 1, 1, 6, 1, 1, 1, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 1, 1, 1, 1, 1, 1, 1, 1},
                        {7, 7, 7, 7, 7, 13, 14, 7, 10, 1, 6, 1, 1, 1, 1, 1, 1, 6, 3, 1, 1, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 1, 1, 1, 1, 1, 1, 1, 1},
                        {2, 2, 2, 2, 2, 6, 9, 7, 14, 7, 8, 3, 1, 1, 1, 1, 11, 8, 1, 3, 3, 17, 7, 7, 7, 7, 7, 7, 7, 13, 7, 8, 1, 1, 1, 1, 1, 1, 1, 1},
                        {2, 2, 2, 2, 11, 8, 2, 2, 6, 2, 2, 3, 3, 1, 1, 1, 6, 1, 1, 3, 3, 6, 1, 1, 1, 1, 1, 1, 1, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {2, 2, 2, 2, 6, 2, 2, 2, 6, 2, 2, 2, 3, 3, 1, 1, 6, 1, 1, 1, 3, 6, 1, 1, 1, 1, 1, 1, 1, 17, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7},
                        {2, 2, 2, 2, 6, 2, 2, 2, 9, 10, 2, 2, 2, 3, 1, 1, 6, 1, 1, 11, 7, 12, 10, 1, 1, 1, 1, 1, 1, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {2, 2, 2, 2, 17, 7, 7, 10, 2, 6, 2, 2, 2, 2, 1, 1, 6, 1, 1, 6, 1, 1, 6, 1, 1, 1, 1, 1, 1, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {2, 2, 2, 2, 6, 2, 2, 6, 2, 6, 2, 2, 2, 2, 2, 1, 6, 1, 1, 6, 1, 1, 6, 1, 1, 1, 1, 1, 1, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {2, 2, 2, 2, 6, 2, 2, 6, 2, 6, 2, 2, 2, 2, 2, 2, 17, 7, 7, 8, 1, 1, 6, 1, 1, 1, 1, 1, 1, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {2, 2, 2, 2, 6, 2, 2, 9,7, 14, 7, 7, 13, 7, 7, 7, 18, 1, 1, 1, 1, 1, 6, 1, 1, 1, 1, 1, 1, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {2, 2, 2, 2, 6, 2, 2, 2, 2, 6, 2, 2, 6, 2, 2, 2, 6, 2, 1, 1, 1, 1, 17, 7, 7, 7, 7, 7, 7, 8, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {2, 2, 2, 2, 6, 2, 2, 2, 2, 6, 2, 2, 6, 2, 2, 2, 6, 2, 1, 1, 1, 1, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {2, 2, 2, 2, 6, 2, 2, 2, 2, 6, 2, 2, 6, 2, 2, 2, 6, 2, 2, 1, 1, 1, 6, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
                };

        mapRows = normalizeRows(mapRows);
        this.map = transposeToXY(mapRows);
    }

    public int[][] getMap() {
        return map;
    }

    public int getWidth() {
        return map.length;
    }

    public int getHeight() {
        return map[0].length;
    }

    public boolean isInside(int x, int y) {
        return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
    }

    public int getTile(int x, int y) {
        return map[x][y];
    }

    public boolean isWalkable(int x, int y) {
        if (!isInside(x, y)) {
            return false;
        }

        int tileId = getTile(x, y);
        TileType tileType = TileType.fromId(tileId);

        if (tileType == null) {
            return false;
        }

        return tileType.isWalkable();
    }

    public List<Point2D> getNeighbors(int x, int y) {
        List<Point2D> neighbors = new ArrayList<>();

        int[][] directions = {
                {1, 0},
                {-1, 0},
                {0, 1},
                {0, -1}
        };

        for (int[] direction : directions) {
            int neighborX = x + direction[0];
            int neighborY = y + direction[1];

            if (isWalkable(neighborX, neighborY)) {
                neighbors.add(new Point2D(neighborX, neighborY));
            }
        }

        return neighbors;
    }

    public List<Point2D> getPath() {
        List<Point2D> path = new ArrayList<>();

        addHorizontalLine(path, 1, 9, 2);
        addVerticalLine(path, 2, 5, 9);
        addHorizontalLine(path, 9, 10, 5);
        addVerticalLine(path, 5, 8, 10);
        addHorizontalLine(path, 10, 16, 8);
        addVerticalLine(path, 8, 12, 16);

        return path;
    }

    private void addHorizontalLine(List<Point2D> path, int startX, int endX, int y) {
        for (int x = startX; x <= endX; x++) {
            path.add(new Point2D(x, y));
        }
    }

    private void addVerticalLine(List<Point2D> path, int startY, int endY, int x) {
        for (int y = startY; y <= endY; y++) {
            path.add(new Point2D(x, y));
        }
    }

    private int[][] normalizeRows(int[][] mapRows) {
        int[][] newRows = new int[TARGET_HEIGHT][TARGET_WIDTH];

        for (int y = 0; y < TARGET_HEIGHT; y++) {
            for (int x = 0; x < TARGET_WIDTH; x++) {
                newRows[y][x] = DEFAULT_TILE;
            }
        }

        for (int y = 0; y < mapRows.length && y < TARGET_HEIGHT; y++) {
            for (int x = 0; x < mapRows[y].length && x < TARGET_WIDTH; x++) {
                newRows[y][x] = mapRows[y][x];
            }
        }

        return newRows;
    }

    private int[][] transposeToXY(int[][] mapRows) {
        int width = mapRows[0].length;
        int height = mapRows.length;
        int[][] mapXY = new int[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                mapXY[x][y] = mapRows[y][x];
            }
        }

        return mapXY;
    }
}