package backend.academy.models;

import java.io.PrintStream;
import java.util.List;
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

    public void mazeVisualisation(PrintStream out, List<Coordinate> trace) {
        String wallSymbol = "⬛";
        String passageSymbol = "⬜";
        String startSymbol = "\uD83D\uDFE9";
        String endSymbol = "🟥";
        String traceSymbol = "🟨";
        String[][] symbolsGrid = new String[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (grid[row][col].type() == Cell.Type.PASSAGE) {
                    symbolsGrid[row][col] = passageSymbol;
                } else {
                    symbolsGrid[row][col] = wallSymbol;
                }
            }
        }

        for (Coordinate coordinate : trace) {
            if (coordinate == trace.getFirst()) {
                symbolsGrid[coordinate.row()][coordinate.col()] = startSymbol;
            } else if (coordinate == trace.getLast()){
                symbolsGrid[coordinate.row()][coordinate.col()] = endSymbol;
            } else {
                symbolsGrid[coordinate.row()][coordinate.col()] = traceSymbol;
            }
        }

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                out.print(symbolsGrid[row][col]);
            }
            out.println();
        }
    }
}
