package backend.academy.generators;

import backend.academy.models.Cell;
import backend.academy.models.Maze;

public interface Generator {
    Maze generate(int height, int width);

    static Cell[][] gridWallFilling(int height, int width) {
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
}
