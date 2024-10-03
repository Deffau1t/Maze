package backend.academy;

import backend.academy.models.Cell;
import backend.academy.models.Maze;
import backend.academy.models.Wall;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static backend.academy.MazeCreationLogic.addWalls;
import static backend.academy.MazeCreationLogic.gridWallFilling;
import static backend.academy.MazeCreationLogic.isInsideMaze;

public class PrimMazeGenerator implements Generator {
    private final Random random = new Random();

    @Override
    public Maze generate(int height, int width) {
        //Заполнение лабиринта стенами
        Cell[][] grid = gridWallFilling(height, width);

        //начинаем со случайной точки
        int startRandomX = random.nextInt(height);
        int startRandomY = random.nextInt(width);
        grid[startRandomX][startRandomY] = new Cell(startRandomX, startRandomY, Cell.Type.PASSAGE);

        //Присоединение к лабиринту стартовой точки
        List<Wall> walls = new ArrayList<>();
        addWalls(startRandomX, startRandomY, walls, grid);

        //Лабиринт оказывается завершенным, когда больше не остается рассматриваемых ребер(стен в списке)
        while (!walls.isEmpty()) {
            // Случайный выбор стены
            Wall wall = walls.remove(random.nextInt(walls.size()));

            // Проверка соседней ячейки
            if (isInsideMaze(wall.neighborX(), wall.neighborY(), grid)
                && grid[wall.neighborX()][wall.neighborY()].type() == Cell.Type.WALL) {
                // Присоединяем проход к лабиринту
                grid[wall.personalX()][wall.personalY()] =
                    new Cell(wall.personalX(), wall.personalY(), Cell.Type.PASSAGE);
                grid[wall.neighborX()][wall.neighborY()] =
                    new Cell(wall.neighborX(), wall.neighborY(), Cell.Type.PASSAGE);
                addWalls(wall.neighborX(), wall.neighborY(), walls, grid);
            }
        }

        return new Maze(height, width, grid);
    }
}
