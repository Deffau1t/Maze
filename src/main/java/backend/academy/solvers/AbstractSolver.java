package backend.academy.solvers;

import backend.academy.models.Cell;
import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractSolver implements Solver {
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        // Отбор всех монеток
        List<Coordinate> coins = selectCoins(maze);

        // При отсутствии монеток найдем просто путь от заданных точек
        if (coins.isEmpty()) {
            return findPath(maze, start, end);
        }

        // Генерируем все возможные вариации коллекционирования монет
        List<List<Coordinate>> coinVariation = generatePermutations(coins);

        List<Coordinate> shortestPath = null;
        int shortestPathLength = Integer.MAX_VALUE;

        for (List<Coordinate> variant : coinVariation) {
            List<Coordinate> path = new ArrayList<>();
            Coordinate current = start;

            for (Coordinate coin : variant) {

                // Находим путь от текущей позиции до монеты
                List<Coordinate> subPath = findPath(maze, current, coin);

                // Если путь не найден, прерываем текущую вариацию
                if (subPath == null) {
                    path = null;
                    break;
                }
                path.addAll(subPath);
                current = coin;
            }

            if (path != null) {
                // Находим путь от последней монеты до конечной точки
                List<Coordinate> finalPath = findPath(maze, current, end);

                // Если путь до конечной точки найден, добавляем его к общему пути
                if (finalPath != null) {
                    path.addAll(finalPath);

                    // Если текущий путь короче самого короткого найденного, обновляем кратчайший путь
                    if (path.size() < shortestPathLength) {
                        shortestPath = path;
                        shortestPathLength = path.size();
                    }
                }
            }
        }

        return shortestPath;
    }

    public List<Coordinate> getNeighbors(Coordinate coordinate, Maze maze) {
        List<Coordinate> neighbors = new ArrayList<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int newRow = coordinate.row() + dir[0];
            int newCol = coordinate.col() + dir[1];

            if (newRow >= 0 && newRow < maze.height() && newCol >= 0 && newCol < maze.width()
                && (maze.grid()[newRow][newCol].type() == Cell.Type.PASSAGE
                || maze.grid()[newRow][newCol].type() == Cell.Type.COIN)) {
                neighbors.add(new Coordinate(newRow, newCol));
            }
        }

        return neighbors;
    }

    private List<Coordinate> selectCoins(Maze maze) {
        List<Coordinate> coinsList = new ArrayList<>();
        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                if (maze.grid()[row][col].type() == Cell.Type.COIN) {
                    coinsList.add(new Coordinate(row, col));
                }
            }
        }
        return coinsList;
    }

    private static List<List<Coordinate>> generatePermutations(List<Coordinate> coins) {
        List<List<Coordinate>> permutations = new ArrayList<>();
        generatePermutationsHelper(coins, new ArrayList<>(), permutations);
        return permutations;
    }

    private static void generatePermutationsHelper(List<Coordinate> remainingCoins,
        List<Coordinate> currentPermutation, List<List<Coordinate>> permutations) {
        if (remainingCoins.isEmpty()) {
            permutations.add(new ArrayList<>(currentPermutation));
            return;
        }

        for (int i = 0; i < remainingCoins.size(); i++) {
            Coordinate coin = remainingCoins.get(i);
            currentPermutation.add(coin);

            // Создаем новый список оставшихся монет без текущей монеты
            List<Coordinate> newRemainingCoins = remainingCoins.stream()
                .filter(c -> !c.equals(coin))
                .collect(Collectors.toList());

            generatePermutationsHelper(newRemainingCoins, currentPermutation, permutations);
            currentPermutation.remove(currentPermutation.size() - 1);
        }
    }

    protected abstract List<Coordinate> findPath(Maze maze, Coordinate start, Coordinate end);
}
