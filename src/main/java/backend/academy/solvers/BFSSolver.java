package backend.academy.solvers;

import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFSSolver extends AbstractSolver {
    @Override
    protected List<Coordinate> findPath(Maze maze, Coordinate start, Coordinate end) {
        boolean[][] visited = new boolean[maze.height()][maze.width()];
        Coordinate[][] parent = new Coordinate[maze.height()][maze.width()];
        Queue<Coordinate> queue = new LinkedList<>();

        queue.add(start);
        visited[start.row()][start.col()] = true;

        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();

            if (current.equals(end)) {

                // Восстановление пути если нашли конечную координату
                List<Coordinate> path = new ArrayList<>();
                Coordinate trace = current;
                while (trace != null) {
                    path.add(trace);
                    trace = parent[trace.row()][trace.col()];
                }

                // Выстраивание пути от начала до конца
                Collections.reverse(path);
                return path;
            }

            // Проходка по соседям
            for (Coordinate neighbor : getNeighbors(current, maze)) {
                if (!visited[neighbor.row()][neighbor.col()]) {
                    visited[neighbor.row()][neighbor.col()] = true;
                    parent[neighbor.row()][neighbor.col()] = current;
                    queue.add(neighbor);
                }
            }
        }
        return null;
    }
}
