import backend.academy.DataValidator;
import backend.academy.exceptions.InvalidNumberException;
import backend.academy.generators.Generator;
import backend.academy.generators.KruskalMazeGenerator;
import backend.academy.generators.RecursiveBacktrackerMazeGenerator;
import backend.academy.models.Cell;
import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import backend.academy.renders.Renderer;
import backend.academy.renders.SimpleRender;
import backend.academy.solvers.AStar;
import backend.academy.solvers.BFS;
import backend.academy.solvers.Solver;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.Test;
import static backend.academy.models.Maze.getPassageList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MazeTest {
    private final Renderer renderer = new SimpleRender();
    private final PrintStream out = System.out;
    private final int mazeSize = 7;
    private final Generator kruskalGenerator = new KruskalMazeGenerator();
    private final Generator recursiveBacktrackerGenerator = new RecursiveBacktrackerMazeGenerator();
    private final DataValidator correctData = new DataValidator();

    @Test
    public void possibleToSolveMazeTest() {
        Maze maze = kruskalGenerator.generate(mazeSize / 2, mazeSize / 2);

        List<Coordinate> passageList = getPassageList(maze);
        Coordinate start = passageList.getFirst();
        Coordinate end = passageList.getLast();

        Solver solver = new BFS();
        List<Coordinate> path = solver.solve(maze, start, end);

        assertNotNull(path, "Путь должен быть нулевым");
        assertEquals(start, path.getFirst(), "Проверка на начальную координату пути");
        assertEquals(end, path.getLast(), "Проверка на конечную координату пути");

        // Вывод лабиринта с найденным путем для визуальной проверки
        out.println(renderer.render(maze, path));
    }

    @Test
    public void impossibleToSolveMazeTest() {
        Maze maze = recursiveBacktrackerGenerator.generate(mazeSize / 2, mazeSize / 2);

        List<Coordinate> passageList = getPassageList(maze);
        Coordinate start = passageList.getFirst();
        Coordinate end = passageList.getLast();

        // Предположим, что лабиринт непроходимый
        maze.grid()[1][1] = new Cell(1, 1, Cell.Type.WALL);

        Solver solver = new AStar();
        List<Coordinate> path = solver.solve(maze, start, end);

        assertTrue(path.isEmpty(), "Не должно быть пути в непроходимом лабиринте");
    }

    @Test
    public void invalidMazeSizeTest() {
        String invalidMazeHeight = "incorrect";
        String invalidMazeWidth = "0";

        InvalidNumberException exceptionHeight = assertThrows(InvalidNumberException.class, () -> {
            correctData.sizeCheck(invalidMazeHeight);
        });

        InvalidNumberException exceptionWidth = assertThrows(InvalidNumberException.class, () -> {
            correctData.sizeCheck(invalidMazeWidth);
        });

        String expectedHeightMessage = "Размерами лабиринта должны являться числа";
        String expectedWidthMessage = "Размерам следует быть в пределах [2, 35]";

        assertEquals(expectedHeightMessage, exceptionHeight.message());
        assertEquals(expectedWidthMessage, exceptionWidth.message());

    }

    @Test
    public void invalidCoordinatesTest() {
        String coordinateRow = "something";
        String coordinateCol = "26";

        InvalidNumberException exceptionCoordinateRow = assertThrows(InvalidNumberException.class, () -> {
            correctData.pointHeightCheck(coordinateRow, mazeSize / 2);
        });

        InvalidNumberException exceptionCoordinateCol = assertThrows(InvalidNumberException.class, () -> {
            correctData.pointWidthCheck(coordinateCol, mazeSize / 2);
        });

        String expectedCoordinateRowMessage = "Координата высоты точки должна быть числом";
        String expectedCoordinateColMessage = "Координата широты точки должна быть в пределах лабиринта";

        assertEquals(expectedCoordinateRowMessage, exceptionCoordinateRow.message());
        assertEquals(expectedCoordinateColMessage, exceptionCoordinateCol.message());

    }
}
