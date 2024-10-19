package backend.academy.renders;

import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import java.util.List;

public class SimpleRender implements Renderer{
    private final String wallSymbol = "⬛";
    private final String passageSymbol = "⬜";
    private final String startSymbol = "🟩";
    private final String endSymbol = "🟥";
    private final String traceSymbol = "🟪";
    private final String coinSymbol = "🟨";
    private final StringBuilder illustration = new StringBuilder();

    @Override
    public String render(Maze maze, Coordinate start, Coordinate end) {
        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                if (row == start.row() && col == start.col()) {
                    illustration.append(startSymbol);
                } else if (row == end.row() && col == end.col()) {
                    illustration.append(endSymbol);
                } else {
                    switch (maze.grid()[row][col].type()) {
                        case PASSAGE -> illustration.append(passageSymbol);
                        case COIN -> illustration.append(coinSymbol);
                        case WALL -> illustration.append(wallSymbol);
                    }
                }
            }
            illustration.append("\n");
        }
        return illustration.toString();
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        String[][] symbolsGrid = new String[maze.height()][maze.width()];
        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                switch (maze.grid()[row][col].type()) {
                    case PASSAGE -> symbolsGrid[row][col] = passageSymbol;
                    case COIN -> symbolsGrid[row][col] = coinSymbol;
                    case WALL -> symbolsGrid[row][col] = wallSymbol;
                }
            }
        }

        for (Coordinate coordinate : path) {
            if (coordinate == path.getFirst()) {
                symbolsGrid[coordinate.row()][coordinate.col()] = startSymbol;
            } else if (coordinate == path.getLast()){
                symbolsGrid[coordinate.row()][coordinate.col()] = endSymbol;
            } else {
                symbolsGrid[coordinate.row()][coordinate.col()] = traceSymbol;
            }
        }

        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                illustration.append(symbolsGrid[row][col]);
            }
            illustration.append("\n");
        }

        return illustration.toString();
    }
}
