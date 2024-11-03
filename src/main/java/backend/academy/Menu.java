package backend.academy;

import backend.academy.exceptions.InvalidCoinsAmount;
import backend.academy.generators.Generator;
import backend.academy.generators.MazeGeneratorFactory;
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
import static backend.academy.DataValidator.FIRST_ERROR_STATUS;
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

            String mazeChoice = scanner.next();
            if (correctData.choiceCheck(mazeChoice) == 0) {
                correctMazeChoice = Integer.parseInt(mazeChoice);
                break;
            } else if (correctData.choiceCheck(mazeChoice) == FIRST_ERROR_STATUS) {
                out.println("Введите тип лабиринта в пределах [1, 2]");
            } else {
                out.println("Тип лабиринта должен являться числом");
            }
        }

        int correctHeight;
        while (true) {
            out.println("Введите высоту лабиринта");

            String height = scanner.next();
            int currentHeight = correctData.sizeCheck(height);
            if (currentHeight > 0) {
                correctHeight = currentHeight;
                break;
            } else if (currentHeight == FIRST_ERROR_STATUS) {
                out.println("Высота должна являться числом");
            } else {
                out.println("Высота должна быть в пределах [3, 100]");
            }
        }

        int correctWidth;
        while (true) {
            out.println("Введите ширину лабиринта");

            String width = scanner.next();
            int currentWidth = correctData.sizeCheck(width);
            if (currentWidth > 0) {
                correctWidth = currentWidth;
                break;
            } else if (currentWidth == FIRST_ERROR_STATUS) {
                out.println("Ширина должна являться числом");
            } else {
                out.println("Ширина должна быть в пределах [3, 100]");
            }
        }

        Generator generator = MazeGeneratorFactory.createGenerator(correctMazeChoice);
        return generator.generate(correctHeight, correctWidth);
    }

    @SuppressWarnings("CyclomaticComplexity")
    public List<Coordinate> choosingPoints(Maze maze) {
        int correctStartPointHeight;
        int correctStartPointWidth;
        while (true) {
            while (true) {
                out.println("Введите координату высоты точки старта");

                String startPointHeight = scanner.next();
                int currentStartPointHeight = correctData.pointHeightCheck(startPointHeight, maze.height());
                if (currentStartPointHeight > 0) {
                    correctStartPointHeight = currentStartPointHeight;
                    break;
                } else if (currentStartPointHeight == FIRST_ERROR_STATUS) {
                    out.println("Координата высоты точки старта должна быть в пределах [3, 99]");
                } else {
                    out.println("Координата высоты точки старта должна являться числом");
                }
            }

            while (true) {
                out.println("Введите координату ширины точки старта");

                String startPointWidth = scanner.next();
                int currentStartPointWidth = correctData.pointWidthCheck(startPointWidth, maze.height());
                if (currentStartPointWidth > 0) {
                    correctStartPointWidth = currentStartPointWidth;
                    break;
                } else if (currentStartPointWidth == FIRST_ERROR_STATUS) {
                    out.println("Координата ширины точки старта должна быть в пределах [1, 99]");
                } else {
                    out.println("Координата ширины точки старта должна являться числом");
                }
            }

            if (maze.grid()[correctStartPointHeight][correctStartPointWidth].type() != Cell.Type.WALL) {
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

                String endPointHeight = scanner.next();
                int currentEndPointHeight = correctData.pointHeightCheck(endPointHeight, maze.height());
                if (currentEndPointHeight > 0) {
                    correctEndPointHeight = currentEndPointHeight;
                    break;
                } else if (currentEndPointHeight == FIRST_ERROR_STATUS) {
                    out.println("Координата высоты конечной точки должна быть в пределах [1, 99]");
                } else {
                    out.println("Координата высоты конечной точки должна являться числом");
                }
            }

            while (true) {
                out.println("Введите координату ширины конечной точки");

                String endPointWidth = scanner.next();
                int currentEndPointWidth = correctData.pointWidthCheck(endPointWidth, maze.height());
                if (currentEndPointWidth > 0) {
                    correctEndPointWidth = currentEndPointWidth;
                    break;
                } else if (currentEndPointWidth == FIRST_ERROR_STATUS) {
                    out.println("Координата ширины конечной точки должна быть в пределах [1, 99]");
                } else {
                    out.println("Координата ширины конечной точки должна являться числом");
                }
            }

            if (correctStartPointHeight == correctEndPointHeight
                && correctStartPointWidth == correctEndPointWidth) {
                out.println("Ваши точки совпали, введите новую");
            } else if (maze.grid()[correctEndPointHeight][correctEndPointWidth].type() != Cell.Type.WALL) {
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

            String solvingChoice = scanner.next();
            if (correctData.choiceCheck(solvingChoice) == 0) {
                correctSolvingChoice = Integer.parseInt(solvingChoice);
                break;
            } else if (correctData.choiceCheck(solvingChoice) == -1) {
                out.println("Введите тип поиска в пределах [1, 2]");
            } else {
                out.println("Выбор поиска должен являться числом");
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
                correctCoinsAmount = correctData.coinsAmountCheck(coinsAmount, passageList.size());
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
