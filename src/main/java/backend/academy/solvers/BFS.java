package backend.academy.solvers;

import backend.academy.models.Cell;
import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS implements Solver {
    private final SolverLogic basicFunctions = new SolverLogic();

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        int height = maze.height();
        int width = maze.width();
        Cell[][] grid = maze.grid();

        boolean[][] visited = new boolean[height][width];
        Coordinate[][] parent = new Coordinate[height][width];
        Queue<Coordinate> queue = new LinkedList<>();

        queue.add(start);
        visited[start.row()][start.col()] = true;

        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();

            if (current.equals(end)) {
                // Восстановление пути
                List<Coordinate> path = new ArrayList<>();
                Coordinate trace = current;
                while (trace != null) {
                    path.add(trace);
                    trace = parent[trace.row()][trace.col()];
                }
                Collections.reverse(path);
                return path;
            }

            for (Coordinate neighbor : basicFunctions.getNeighbors(current, grid, height, width)) {
                if (!visited[neighbor.row()][neighbor.col()]) {
                    visited[neighbor.row()][neighbor.col()] = true;
                    parent[neighbor.row()][neighbor.col()] = current;
                    queue.add(neighbor);
                }
            }
        }

        return null; // Путь не найден
    }
}
