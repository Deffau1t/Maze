package backend.academy.solvers;

import backend.academy.models.Cell;
import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import backend.academy.models.Node;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SolverLogic {
    public static List<Coordinate> getNeighbors(Coordinate coordinate, Maze maze) {
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

    public static int heuristic(Coordinate a, Coordinate b) {
        return Math.abs(a.row() - b.row()) + Math.abs(a.col() - b.col());
    }

    public static List<Coordinate> reconstructPath(Node node) {
        List<Coordinate> path = new ArrayList<>();
        while (node != null) {
            path.add(node.position);
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    }
}
