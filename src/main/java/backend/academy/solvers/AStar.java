package backend.academy.solvers;

import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import backend.academy.models.Node;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import static backend.academy.solvers.SolverLogic.getNeighbors;
import static backend.academy.solvers.SolverLogic.heuristic;
import static backend.academy.solvers.SolverLogic.reconstructPath;

public class AStar implements Solver{
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.f));
        Map<Coordinate, Node> allNodes = new HashMap<>();

        Node startNode = new Node(start, null, 0, heuristic(start, end));
        openSet.add(startNode);
        allNodes.put(start, startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.position.equals(end)) {
                return reconstructPath(current);
            }

            for (Coordinate neighbor : getNeighbors(current.position, maze)) {
                int tentativeG = current.g + 1;
                Node neighborNode = allNodes.getOrDefault(neighbor, new Node(neighbor, null, Integer.MAX_VALUE, 0));

                if (tentativeG < neighborNode.g) {
                    neighborNode.parent = current;
                    neighborNode.g = tentativeG;
                    neighborNode.f = tentativeG + heuristic(neighbor, end);

                    if (!allNodes.containsKey(neighbor)) {
                        openSet.add(neighborNode);
                        allNodes.put(neighbor, neighborNode);
                    }
                }
            }
        }
        return Collections.emptyList(); // Путь не найден
    }
}
