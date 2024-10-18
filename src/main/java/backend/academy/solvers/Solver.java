package backend.academy.solvers;

import backend.academy.models.Cell;
import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import java.util.ArrayList;
import java.util.List;

public interface Solver {
    List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end);

    static List<Coordinate> getNeighbors(Coordinate coordinate, Maze maze) {
        List<Coordinate> neighbors = new ArrayList<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int newRow = coordinate.row() + dir[0];
            int newCol = coordinate.col() + dir[1];

            if (newRow >= 0 && newRow < maze.height() && newCol >= 0 && newCol < maze.width() &&
                maze.grid()[newRow][newCol].type() == Cell.Type.PASSAGE) {
                neighbors.add(new Coordinate(newRow, newCol));
            }
        }

        return neighbors;
    }
}
