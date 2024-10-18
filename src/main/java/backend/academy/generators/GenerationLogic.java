package backend.academy.generators;

import backend.academy.models.Cell;
import backend.academy.models.Coordinate;
import backend.academy.models.Wall;
import java.util.List;

public class GenerationLogic {
    public static Cell[][] gridWallFilling(int height, int width) {
        Cell[][] grid = new Cell[height * 2 + 1][width * 2 + 1];
        for (int row = 0; row < height * 2 + 1; row++) {
            for (int col = 0; col < width * 2 + 1; col++) {
                if (row % 2 == 1 && col % 2 == 1) {
                    grid[row][col] = new Cell(row, col, Cell.Type.PASSAGE);
                } else {
                    grid[row][col] = new Cell(row, col, Cell.Type.WALL);
                }
            }
        }
        return grid;
    }

    private static int getGridMaxWidth(Cell[][] grid) {
        return grid[0].length;
    }

    private static int getGridMaxHeight(Cell[][] grid) {
        return grid.length;
    }

    public static boolean isInsideMaze(int row, int col, Cell[][] grid) {
        return row >= 0 && row < getGridMaxHeight(grid) && col >= 0 && col < getGridMaxWidth(grid);
    }

    public static boolean isInsideMaze(Coordinate coordinate, Cell[][] grid) {
        return isInsideMaze(coordinate.row(), coordinate.col(), grid);
    }

        public static void addWalls(int col, int row, List<Wall> walls, Cell[][] grid) {
        // добавим стену при возможности слева
        if (col > 0) {
            walls.add(new Wall(col - 1, row, col - 2, row));
        }
        // справа
        if (col < getGridMaxWidth(grid) - 1) {
            walls.add(new Wall(col + 1, row, col + 2, row));
        }
        // сверху
        if (row > 0) {
            walls.add(new Wall(col, row - 1, col, row - 2));
        }
        // снизу
        if (row < getGridMaxHeight(grid) - 1) {
            walls.add(new Wall(col, row + 1, col, row + 2));
        }
    }
}
