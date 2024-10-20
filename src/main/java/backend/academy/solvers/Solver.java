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
                (maze.grid()[newRow][newCol].type() == Cell.Type.PASSAGE ||
                maze.grid()[newRow][newCol].type() == Cell.Type.COIN)) {
                neighbors.add(new Coordinate(newRow, newCol));
            }
        }

        return neighbors;
    }

    static List<Coordinate> selectCoins(Maze maze) {
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

    static List<List<Coordinate>> generatePermutations(List<Coordinate> coins) {
        List<List<Coordinate>> permutations = new ArrayList<>();
        generatePermutationsHelper(coins, new ArrayList<>(), permutations);
        return permutations;
    }

    static void generatePermutationsHelper(List<Coordinate> coins, List<Coordinate> current, List<List<Coordinate>> permutations) {
        if (coins.isEmpty()) {
            permutations.add(new ArrayList<>(current));
            return;
        }

        for (int i = 0; i < coins.size(); i++) {
            Coordinate coin = coins.get(i);
            current.add(coin);
            List<Coordinate> remainingCoins = new ArrayList<>(coins);
            remainingCoins.remove(i);
            generatePermutationsHelper(remainingCoins, current, permutations);
            current.remove(current.size() - 1);
        }
    }
}
