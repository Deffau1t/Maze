import backend.academy.DataValidator;
import backend.academy.generators.Generator;
import backend.academy.generators.KruskalMazeGenerator;
import backend.academy.generators.RecursiveBacktrackerMazeGenerator;
import backend.academy.models.Cell;
import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import backend.academy.renders.MazeRender;
import backend.academy.renders.Renderer;
import backend.academy.solvers.AStarSolver;
import backend.academy.solvers.BFSSolver;
import backend.academy.solvers.Solver;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.Test;
import static backend.academy.DataValidator.FIRST_ERROR_STATUS;
import static backend.academy.DataValidator.SECOND_ERROR_STATUS;
import static backend.academy.models.Maze.getPassageList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MazeTest {
    private final Renderer renderer = new MazeRender();
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

        Solver solver = new BFSSolver();
        List<Coordinate> path = solver.solve(maze, start, end);

        assertNotNull(path, "Путь должен быть нулевым");
        assertEquals(start, path.getFirst(), "Проверка на начальную координату пути");
        assertEquals(end, path.getLast(), "Проверка на конечную координату пути");

        // Вывод лабиринта с найденным путем для визуальной проверки
        out.println(renderer.renderMazeWithPath(maze, path));
    }

    @Test
    public void impossibleToSolveMazeTest() {
        Maze maze = recursiveBacktrackerGenerator.generate(mazeSize / 2, mazeSize / 2);

        List<Coordinate> passageList = getPassageList(maze);
        Coordinate start = passageList.getFirst();
        Coordinate end = passageList.getLast();

        // Предположим, что лабиринт непроходимый
        maze.grid()[1][1] = new Cell(1, 1, Cell.Type.WALL);

        Solver solver = new AStarSolver();
        List<Coordinate> path = solver.solve(maze, start, end);

        assertTrue(path.isEmpty(), "Не должно быть пути в непроходимом лабиринте");
    }

    @Test
    public void invalidMazeSizeTest() {
        String invalidMazeHeight = "incorrect";
        String invalidMazeWidth = "0";

        assertEquals(correctData.sizeCheck(invalidMazeHeight), FIRST_ERROR_STATUS);
        assertEquals(correctData.sizeCheck(invalidMazeWidth), SECOND_ERROR_STATUS);
    }

    @Test
    public void invalidCoordinatesTest() {
        String coordinateRow = "something";
        String coordinateCol = "111";

        assertEquals(correctData.sizeCheck(coordinateRow), FIRST_ERROR_STATUS);
        assertEquals(correctData.sizeCheck(coordinateCol), SECOND_ERROR_STATUS);

    }
}
