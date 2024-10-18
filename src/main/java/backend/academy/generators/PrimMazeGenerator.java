package backend.academy.generators;

import backend.academy.models.Cell;
import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PrimMazeGenerator implements Generator {

    @Override
    public Maze generate(int height, int width) {
        Cell[][] grid = new Cell[height * 2 + 1][width * 2 + 1];
        List<Coordinate> walls = new ArrayList<>();
        Random random = new Random();

        // Инициализация сетки
        for (int row = 0; row < height * 2 + 1; row++) {
            for (int col = 0; col < width * 2 + 1; col++) {
                if (row % 2 == 1 && col % 2 == 1) {
                    grid[row][col] = new Cell(row, col, Cell.Type.PASSAGE);
                } else {
                    grid[row][col] = new Cell(row, col, Cell.Type.WALL);
                }
            }
        }

        // Начинаем с произвольной ячейки
        int startRow = random.nextInt(height) * 2 + 1;
        int startCol = random.nextInt(width) * 2 + 1;
        grid[startRow][startCol] = new Cell(startRow, startCol, Cell.Type.PASSAGE);

        // Добавляем все стены вокруг начальной ячейки в список
        if (startRow > 1) {
            walls.add(new Coordinate(startRow - 1, startCol));
        }
        if (startRow < height * 2 - 1) {
            walls.add(new Coordinate(startRow + 1, startCol));
        }
        if (startCol > 1) {
            walls.add(new Coordinate(startRow, startCol - 1));
        }
        if (startCol < width * 2 - 1) {
            walls.add(new Coordinate(startRow, startCol + 1));
        }

        // Алгоритм Прима
        while (!walls.isEmpty()) {
            int index = random.nextInt(walls.size());
            Coordinate wall = walls.get(index);
            walls.remove(index);

            List<Coordinate> neighbors = getNeighbors(wall, height, width);
            Collections.shuffle(neighbors);

            for (Coordinate neighbor : neighbors) {
                if (grid[neighbor.row()][neighbor.col()].type() == Cell.Type.WALL) {
                    grid[wall.row()][wall.col()] = new Cell(wall.row(), wall.col(), Cell.Type.PASSAGE);
                    grid[neighbor.row()][neighbor.col()] = new Cell(neighbor.row(), neighbor.col(), Cell.Type.PASSAGE);

                    if (neighbor.row() > 1) {
                        walls.add(new Coordinate(neighbor.row() - 1, neighbor.col()));
                    }
                    if (neighbor.row() < height * 2 - 1) {
                        walls.add(new Coordinate(neighbor.row() + 1, neighbor.col()));
                    }
                    if (neighbor.col() > 1) {
                        walls.add(new Coordinate(neighbor.row(), neighbor.col() - 1));
                    }
                    if (neighbor.col() < width * 2 - 1) {
                        walls.add(new Coordinate(neighbor.row(), neighbor.col() + 1));
                    }
                    break;
                }
            }
        }

        return new Maze(height * 2 + 1, width * 2 + 1, grid);
    }

    private List<Coordinate> getNeighbors(Coordinate wall, int height, int width) {
        List<Coordinate> neighbors = new ArrayList<>();
        int row = wall.row();
        int col = wall.col();

        if (row > 1) {
            neighbors.add(new Coordinate(row - 2, col));
        }
        if (row < height * 2 - 1) {
            neighbors.add(new Coordinate(row + 2, col));
        }
        if (col > 1) {
            neighbors.add(new Coordinate(row, col - 2));
        }
        if (col < width * 2 - 1) {
            neighbors.add(new Coordinate(row, col + 2));
        }

        return neighbors;
    }
}
