package backend.academy;

import backend.academy.exceptions.InvalidCoinsAmount;
import backend.academy.exceptions.InvalidNumberException;
import backend.academy.generators.Generator;
import backend.academy.generators.KruskalMazeGenerator;
import backend.academy.generators.RecursiveBacktrackerMazeGenerator;
import backend.academy.models.Cell;
import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import backend.academy.renders.MazeRender;
import backend.academy.renders.Renderer;
import backend.academy.solvers.MazeSolverFactory;
import backend.academy.solvers.Solver;
import java.io.PrintStream;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import static backend.academy.DataValidator.COINS_MAZE_CAPACITY;
import static backend.academy.models.Maze.getPassageList;
import static backend.academy.solvers.AbstractSolver.getNeighbors;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final PrintStream out = System.out;
    private final DataValidator correctData = new DataValidator();
    private final Renderer mazeRenderer = new MazeRender();

    public Maze mazeGeneration() {

        int correctMazeChoice;
        while (true) {
            out.print("""
                Выберите тип лабиринта:
                1 - Алгоритм Recursive backtracker
                2 - Алгоритм Краскала
                """);

            try {
                String mazeChoice = scanner.next();
                correctMazeChoice = correctData.choiceCheck(mazeChoice);
                break;
            } catch (InvalidNumberException e) {
                out.println(e.message());
            }
        }

        int correctHeight;
        while (true) {
            out.println("Введите высоту лабиринта");

            try {
                String width = scanner.next();
                correctHeight = correctData.sizeCheck(width);
                break;
            } catch (InvalidNumberException e) {
                out.println(e.message());
            }
        }

        int correctWidth;
        while (true) {
            out.println("Введите ширину лабиринта");

            try {
                String width = scanner.next();
                correctWidth = correctData.sizeCheck(width);
                break;
            } catch (InvalidNumberException e) {
                out.println(e.message());
            }
        }

        Generator generator;
        Maze maze;

        if (correctMazeChoice == 1) {
            generator = new RecursiveBacktrackerMazeGenerator();
        } else {
            generator = new KruskalMazeGenerator();
        }
        maze = generator.generate(correctHeight, correctWidth);
        return maze;
    }

    public List<Coordinate> choosingPoints(Maze maze) {
        int correctStartPointHeight;
        int correctStartPointWidth;
        while (true) {
            while (true) {
                out.println("Введите координату высоты точки старта");

                try {
                    String startPointHeight = scanner.next();
                    correctStartPointHeight = correctData.pointHeightCheck(startPointHeight, maze.height());
                    break;
                } catch (InvalidNumberException e) {
                    out.println(e.message());
                }
            }

            while (true) {
                out.println("Введите координату широты точки старта");

                try {
                    String startPointWidth = scanner.next();
                    correctStartPointWidth = correctData.pointWidthCheck(startPointWidth, maze.width());
                    break;
                } catch (InvalidNumberException e) {
                    out.println(e.message());
                }
            }
            if (maze.grid()[correctStartPointHeight][correctStartPointWidth].type() == Cell.Type.PASSAGE) {
                break;
            } else {
                out.println("Ваша стартовая точка оказалась стеной, выберете другую");
                out.println("Ближайшие проходы для данной стартовой точки");
                out.println(getNeighbors(new Coordinate(correctStartPointHeight, correctStartPointWidth), maze));
            }
        }

        int correctEndPointHeight;
        int correctEndPointWidth;
        while (true) {
            while (true) {
                out.println("Введите координату высоты конечной точки");

                try {
                    String endPointHeight = scanner.next();
                    correctEndPointHeight = correctData.pointHeightCheck(endPointHeight, maze.height());
                    break;
                } catch (InvalidNumberException e) {
                    out.println(e.message());
                }
            }

            while (true) {
                out.println("Введите координату широты конечной точки");

                try {
                    String endPointWidth = scanner.next();
                    correctEndPointWidth = correctData.pointWidthCheck(endPointWidth, maze.width());
                    break;
                } catch (InvalidNumberException e) {
                    out.println(e.message());
                }
            }

            if (correctStartPointHeight == correctEndPointHeight
                && correctStartPointWidth == correctEndPointWidth) {
                out.println("Ваши точки совпали, введите новую");
            } else if (maze.grid()[correctEndPointHeight][correctEndPointWidth].type() == Cell.Type.PASSAGE) {
                break;
            } else {
                out.println("Ваша конечная точка оказалась стеной, выберете другую");
                out.println("Ближайшие проходы для данной конечной точки");
                out.println(getNeighbors(new Coordinate(correctEndPointHeight, correctEndPointWidth), maze));
            }
        }
        return List.of(new Coordinate(correctStartPointHeight, correctStartPointWidth),
            new Coordinate(correctEndPointHeight, correctEndPointWidth));
    }

    public void mazeSolving(Maze maze) {
        droppingCoins(maze);
        List<Coordinate> pointsCoordinates = choosingPoints(maze);
        Coordinate startInd = pointsCoordinates.getFirst();
        Coordinate endInd = pointsCoordinates.getLast();

        out.println("Сгенерированный лабиринт с точками");
        out.println(mazeRenderer.renderMazeWithCoordinates(maze, startInd, endInd));

        int correctSolvingChoice;
        while (true) {
            out.print("""
                Выберите алгоритм поиска пути в лабиринте:
                1 - Алгоритм BFS
                2 - Алгоритм A*(A-star)
                """);

            try {
                String solvingChoice = scanner.next();
                correctSolvingChoice = correctData.choiceCheck(solvingChoice);
                break;
            } catch (InvalidNumberException e) {
                out.println(e.message());
            }
        }

        Solver solver = MazeSolverFactory.createSolver(correctSolvingChoice);

        try {
            List<Coordinate> path = solver.solve(maze, startInd, endInd);

            if (path.size() != 2) {
                out.println(mazeRenderer.renderMazeWithPath(maze, path));
            } else {
                out.println("Путь отсутствует");
                out.println(
                    mazeRenderer.renderMazeWithCoordinates(maze, startInd, endInd));
            }
        } catch (NullPointerException e) {
            out.println(e);
        }
    }

    public void droppingCoins(Maze maze) {
        List<Coordinate> passageList = getPassageList(maze);
        int correctCoinsAmount;
        while (true) {
            out.println("Введите количество монеток в лабиринте(не больше " + COINS_MAZE_CAPACITY + ")");
            try {
                String coinsAmount = scanner.next();
                correctCoinsAmount = correctData.coinsAmountCheck(coinsAmount);
                break;
            } catch (InvalidCoinsAmount e) {
                out.println(e.message());
            }
        }

        for (int currentCoin = 0; currentCoin < correctCoinsAmount; currentCoin++) {
            Random random = new Random();
            int randomPassageIndex = random.nextInt(passageList.size());
            Coordinate randomCoin = passageList.get(randomPassageIndex);
            maze.grid()[randomCoin.row()][randomCoin.col()] =
                new Cell(randomCoin.row(), randomCoin.col(), Cell.Type.COIN);
            passageList.remove(randomPassageIndex);
        }
    }
}
