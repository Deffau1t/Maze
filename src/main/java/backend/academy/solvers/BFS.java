package backend.academy.solvers;

import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS implements Solver {
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        List<Coordinate> coins = Solver.selectCoins(maze);
        if (coins.isEmpty()) {
            // Если монет нет, просто найдем путь от старта до финиша
            return findPath(maze, start, end);
        }

        // Генерируем все возможные последовательности прохождения монет
        List<List<Coordinate>> coinSequences = Solver.generatePermutations(coins);

        List<Coordinate> shortestPath = null;
        int shortestPathLength = Integer.MAX_VALUE;

        for (List<Coordinate> sequence : coinSequences) {
            List<Coordinate> path = new ArrayList<>();
            Coordinate current = start;

            for (Coordinate coin : sequence) {
                List<Coordinate> subPath = findPath(maze, current, coin);
                if (subPath == null) {
                    path = null;
                    break;
                }
                path.addAll(subPath);
                current = coin;
            }

            if (path != null) {
                List<Coordinate> finalPath = findPath(maze, current, end);
                if (finalPath != null) {
                    path.addAll(finalPath);
                    if (path.size() < shortestPathLength) {
                        shortestPath = path;
                        shortestPathLength = path.size();
                    }
                }
            }
        }

        return shortestPath;
    }

    private List<Coordinate> findPath(Maze maze, Coordinate start, Coordinate end) {
        int height = maze.height();
        int width = maze.width();

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

            for (Coordinate neighbor : Solver.getNeighbors(current, maze)) {
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
