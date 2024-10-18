package backend.academy.solvers;

import backend.academy.models.Cell;
import backend.academy.models.Coordinate;
import java.util.ArrayList;
import java.util.List;

public class SolverLogic {
    List<Coordinate> getNeighbors(Coordinate coord, Cell[][] grid, int height, int width) {
        List<Coordinate> neighbors = new ArrayList<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int newRow = coord.row() + dir[0];
            int newCol = coord.col() + dir[1];

            if (newRow >= 0 && newRow < height && newCol >= 0 && newCol < width &&
                grid[newRow][newCol].type() == Cell.Type.PASSAGE) {
                neighbors.add(new Coordinate(newRow, newCol));
            }
        }

        return neighbors;
    }

}
