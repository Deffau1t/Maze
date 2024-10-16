package backend.academy;

import backend.academy.exceptions.InvalidNumberException;

public class DataValidator {
    private final String intMatching = "-?\\d+";
    private final int mazeSizeMaximum = 35;
    private final int mazeSizeMinimum = 3;

    int mazeChoiceCheck(String mazeChoice) throws InvalidNumberException {
        if (mazeChoice.matches(intMatching)) {
            int integerMazeChoice = Integer.parseInt(mazeChoice);
            if (integerMazeChoice >= 1 && integerMazeChoice <= 2) {
                return integerMazeChoice;
            } else {
                throw new InvalidNumberException("Введите число в пределах [1, 2]");
            }
        } else {
            throw new InvalidNumberException("Выбором лабиринта должно являться число");
        }
    }

    int sizeCheck(String length) throws InvalidNumberException {
        if (length.matches(intMatching)) {
            int integerLength = Integer.parseInt(length);
            if (integerLength >= mazeSizeMinimum && integerLength <= mazeSizeMaximum) {
                return integerLength;
            } else {
                throw new InvalidNumberException("Размерам следует быть в пределах [3, 35]");
            }
        } else {
            throw new InvalidNumberException("Размерами лабиринта должны являться числа");
        }
    }

    int pointHeightCheck(String pointHeightCheck, int height) throws InvalidNumberException {
        if (pointHeightCheck.matches(intMatching)) {
            int integerPointHeightCheck = Integer.parseInt(pointHeightCheck);
            if (integerPointHeightCheck >= 1 && integerPointHeightCheck <= height) {
                return integerPointHeightCheck;
            } else {
                throw new InvalidNumberException("Координата высоты точки должна быть в пределах лабиринта");
            }
        } else {
            throw new InvalidNumberException("Координата высоты точки должна быть числом");
        }
    }

    int pointWidthCheck(String pointWidthCheck, int width) throws InvalidNumberException {
        if (pointWidthCheck.matches(intMatching)) {
            int integerPointWidthCheck = Integer.parseInt(pointWidthCheck);
            if (integerPointWidthCheck >= 1 && integerPointWidthCheck <= width) {
                return integerPointWidthCheck;
            } else {
                throw new InvalidNumberException("Координата широты точки должна быть в пределах лабиринта");
            }
        } else {
            throw new InvalidNumberException("Координата широты точки должна быть числом");
        }
    }
}
