package backend.academy.generators;

import backend.academy.models.Cell;
import backend.academy.models.Maze;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RecursiveBacktrackerMazeGenerator implements Generator {
    /**
     * Описание алгоритма генерации лабиринта RecursiveBacktracker:
     * 1. Создается сетка, представляющая лабиринт, где все ячейки изначально являются стенами.
     * 2. Выбираем случайную ячейку в сетке и отмечаем её как посещённую.
     * 3. Создаем проходы:
     * Пока есть посещённые ячейки:
     * Выбираем текущую ячейку и находим все ее соседние ячейки, которые еще не посещены.
     * При наличии:
     *  Выберем одну из них случайным образом.
     *  Удалим стену между текущей ячейкой и выбранной.
     *  Запоминаем текущую ячейку в стеке и перемещаемся в выбранную ячейку, отметив её как посещённую.
     * При отсутствии:
     *  Возвращаемся к предыдущей ячейке, извлекая её из стека.
     * 4. Когда стек станет пустым -> все пути прошли.
     */
    @Override
    public Maze generate(int height, int width) {
        boolean[][] visited = new boolean[height][width];

        // Инициализация сетки
        Cell[][] grid = Generator.gridWallFilling(height, width);

        // Выбираем случайную начальную ячейку
        Random random = new Random();
        int startRow = random.nextInt(height);
        int startCol = random.nextInt(width);

        // Запускаем рекурсивный алгоритм
        recursiveBacktrack(grid, visited, startRow, startCol, height, width);

        return new Maze(height * 2 + 1, width * 2 + 1, grid);
    }

    private void recursiveBacktrack(Cell[][] grid, boolean[][] visited, int row, int col, int height, int width) {
        visited[row][col] = true;

        // Список возможных направлений
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        List<int[]> shuffledDirections = Arrays.asList(directions);
        Collections.shuffle(shuffledDirections);

        for (int[] direction : shuffledDirections) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (newRow >= 0 && newRow < height && newCol >= 0 && newCol < width && !visited[newRow][newCol]) {
                // Удаляем стену между текущей ячейкой и новой ячейкой
                int wallRow = row * 2 + 1 + direction[0];
                int wallCol = col * 2 + 1 + direction[1];
                grid[wallRow][wallCol] = new Cell(wallRow, wallCol, Cell.Type.PASSAGE);

                // Рекурсивно вызываем алгоритм для новой ячейки
                recursiveBacktrack(grid, visited, newRow, newCol, height, width);
            }
        }
    }
}
