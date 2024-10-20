package backend.academy.solvers;

import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import backend.academy.models.Node;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class AStar implements Solver{
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

    private List<Coordinate> findPath(Maze maze, Coordinate start, Coordinate goal) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.f));
        Map<Coordinate, Node> allNodes = new HashMap<>();

        Node startNode = new Node(start, null, 0, heuristic(start, goal));
        openSet.add(startNode);
        allNodes.put(start, startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.position.equals(goal)) {
                return reconstructPath(current);
            }

            for (Coordinate neighbor : Solver.getNeighbors(current.position, maze)) {
                int tentativeG = current.g + 1;
                Node neighborNode = allNodes.getOrDefault(neighbor, new Node(neighbor, null, Integer.MAX_VALUE, 0));

                if (tentativeG < neighborNode.g) {
                    neighborNode.parent = current;
                    neighborNode.g = tentativeG;
                    neighborNode.f = tentativeG + heuristic(neighbor, goal);

                    if (!allNodes.containsKey(neighbor)) {
                        openSet.add(neighborNode);
                        allNodes.put(neighbor, neighborNode);
                    }
                }
            }
        }

        return Collections.emptyList(); // Путь не найден
    }

    private static int heuristic(Coordinate a, Coordinate b) {
        return Math.abs(a.row() - b.row()) + Math.abs(a.col() - b.col());
    }

    private static List<Coordinate> reconstructPath(Node node) {
        List<Coordinate> path = new ArrayList<>();
        while (node != null) {
            path.add(node.position);
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    }
}
