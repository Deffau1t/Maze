package backend.academy;

import backend.academy.exceptions.InvalidNumberException;
import backend.academy.generators.KruskalMazeGenerator;
import backend.academy.generators.PrimMazeGenerator;
import backend.academy.models.Cell;
import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import java.io.PrintStream;
import java.util.Scanner;

public class Menu {
    public void startMazeGeneration() {
        Scanner scanner = new Scanner(System.in);
        PrintStream out = System.out;
        DataValidator correctData = new DataValidator();

        int correctMazeChoice;
        while (true) {
            out.print("""
                Выберите тип лабиринта:
                1 - Алгоритм Прима
                2 - Алгоритм Краскала
                """);

            try {
                String mazeChoice = scanner.next();
                correctMazeChoice = correctData.mazeChoiceCheck(mazeChoice);
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

        Generator generator;
        Maze maze;

        if (correctMazeChoice == 1) {
            generator = new PrimMazeGenerator();
        } else {
            generator = new KruskalMazeGenerator();
        }
        maze = generator.generate(correctHeight, correctWidth);

        int correctStartPointHeight;
        int correctStartPointWidth;
        while (true) {
            while (true) {
                out.println("Введите координату высоты точки старта");

                try {
                    String startPointHeight = scanner.next();
                    correctStartPointHeight = correctData.pointHeightCheck(startPointHeight, correctHeight);
                    break;
                } catch (InvalidNumberException e) {
                    out.println(e.message());
                }
            }

            while (true) {
                out.println("Введите координату широты точки старта");

                try {
                    String startPointWidth = scanner.next();
                    correctStartPointWidth = correctData.pointWidthCheck(startPointWidth, correctWidth);
                    break;
                } catch (InvalidNumberException e) {
                    out.println(e.message());
                }
            }
            if (maze.grid()[correctStartPointHeight][correctStartPointWidth].type() == Cell.Type.PASSAGE) {
                break;
            } else {
                out.println("Ваша стартовая точка оказалась стеной, выберете другую");
            }
        }

        int correctEndPointHeight;
        int correctEndPointWidth;
        while (true) {
            while (true) {
                out.println("Введите координату высоты конечной точки");

                try {
                    String endPointHeight = scanner.next();
                    correctEndPointHeight = correctData.pointHeightCheck(endPointHeight, correctHeight);
                    break;
                } catch (InvalidNumberException e) {
                    out.println(e.message());
                }
            }

            while (true) {
                out.println("Введите координату широты конечной точки");

                try {
                    String endPointWidth = scanner.next();
                    correctEndPointWidth = correctData.pointWidthCheck(endPointWidth, correctWidth);
                    break;
                } catch (InvalidNumberException e) {
                    out.println(e.message());
                }
            }

            if (maze.grid()[correctEndPointHeight][correctEndPointWidth].type() == Cell.Type.PASSAGE) {
                break;
            } else {
                out.println("Ваша конечная точка оказалась стеной, выберете другую");
            }
        }
        Coordinate startCoordinate = new Coordinate(correctStartPointHeight, correctStartPointWidth);
        Coordinate endCoordinate = new Coordinate(correctEndPointHeight, correctEndPointWidth);
        maze.mazeVisualisation(out, startCoordinate, endCoordinate);
    }
}
