package backend.academy;

import backend.academy.models.Cell;
import backend.academy.models.Wall;
import java.util.List;

class MazeCreationLogic {
    private static final int MAXIMUM_WIDTH_INDEX = 0;

    static Cell[][] gridWallFilling(int height, int width) {
        Cell[][] grid = new Cell[height][width];
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                grid[x][y] = new Cell(x, y, Cell.Type.WALL);
            }
        }
        return grid;
    }

    static void addWalls(int x, int y, List<Wall> walls, Cell[][] grid) {
        // добавим стену при возможности слева
        if (x > 0) {
            walls.add(new Wall(x - 1, y, x - 2, y));
        }
        // справа
        if (x < grid.length - 1) {
            walls.add(new Wall(x + 1, y, x + 2, y));
        }
        // сверху
        if (y > 0) {
            walls.add(new Wall(x, y - 1, x, y - 2));
        }
        // снизу
        if (y < grid[MAXIMUM_WIDTH_INDEX].length - 1) {
            walls.add(new Wall(x, y + 1, x, y + 2));
        }
    }

    static boolean isInsideMaze(int x, int y, Cell[][] grid) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[MAXIMUM_WIDTH_INDEX].length;
    }
}
