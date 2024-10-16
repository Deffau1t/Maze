package backend.academy.models;

import java.io.PrintStream;
import lombok.Getter;

@Getter
public final class Maze {
    private final int height;
    private final int width;
    private final Cell[][] grid;

    public Maze(int height, int width, Cell[][] grid) {
        this.height = height;
        this.width = width;
        this.grid = grid;
    }

    public void mazeVisualisation(PrintStream out, Coordinate startCoordinate, Coordinate endCoordinate) {
        String wallSymbol = "□";
        String startSymbol = "\uD83C\uDD70";
        String endSymbol = "\uD83C\uDD71";
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row == startCoordinate.row() && col == startCoordinate.col()) {
                    out.print(startSymbol);
                } else if (row == endCoordinate.row() && col == endCoordinate.col()) {
                    out.print(endSymbol);
                } else {
                    out.print(grid[row][col].type() == Cell.Type.PASSAGE ? " " : wallSymbol);
                }
            }
            out.println();
        }
    }
}
