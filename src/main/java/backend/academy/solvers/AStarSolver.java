package backend.academy.solvers;

import backend.academy.models.Cell;
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

public class AStarSolver extends AbstractSolver {
    /**
     * Описание алгоритма поиска пути A*
     * 1. Будем работать с приоритетной очередью для множества узлов, сортируемых по 'f' и хэш-мапой для их хранения.
     * 2. Начнем с узла с координатами начальной точки и добавим его в нашу хэш-мапу.
     * 3. До тех пор, пока наше множество не пустое:
     *          Извлечем узел с наименьшим 'f', если он не является конечной координатой:
     *              Пройдемся по всем соседям:
     *                  Создаем узлы соседей, если их 'g' меньше текущего, обновим его родителя
     *                  и соответствующие значения 'g' и 'f'.
     *                  Добавим соседа при его отсутствии в нашем множестве.
     *         При совпадении текущей и конечной, вернем путь,
     *         восстановив его от конечного узла до стартового(перевернув).
     */
    @Override
    protected List<Coordinate> findPath(Maze maze, Coordinate start, Coordinate end) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.f));
        Map<Coordinate, Node> allNodes = new HashMap<>();

        // Проверка, что выбранная точка не стена
        if (maze.grid()[start.row()][start.col()].type() != Cell.Type.WALL) {
            Node startNode = new Node(start, null, 0, heuristic(start, end));
            openSet.add(startNode);
            allNodes.put(start, startNode);
        }

        while (!openSet.isEmpty()) {
            // Извлечем узел с наименьшим f
            Node current = openSet.poll();

            if (current.position.equals(end)) {
                return reconstructPath(current);
            }

            // Работаем с соседями текущего узла
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

        return Collections.emptyList();
    }

    // Вычисление эвристического расстояния
    private static int heuristic(Coordinate a, Coordinate b) {
        return Math.abs(a.row() - b.row()) + Math.abs(a.col() - b.col());
    }

    // Метод для восстановления пути до стартового
    private static List<Coordinate> reconstructPath(Node node) {
        List<Coordinate> path = new ArrayList<>();
        Node currentNode = node;
        while (currentNode != null) {
            path.add(currentNode.position);
            currentNode = currentNode.parent;
        }
        Collections.reverse(path);
        return path;
    }
}
