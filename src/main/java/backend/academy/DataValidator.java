package backend.academy;

import backend.academy.exceptions.InvalidCoinsAmount;
import backend.academy.exceptions.InvalidNumberException;

public class DataValidator {
    private final String intMatching = "-?\\d+";
    private final int mazeSizeMaximum = 35;
    private final int mazeSizeMinimum = 2;

    public int choiceCheck(String choice) throws InvalidNumberException {
        if (choice.matches(intMatching)) {
            int integerChoice = Integer.parseInt(choice);
            if (integerChoice >= 1 && integerChoice <= 2) {
                return integerChoice;
            } else {
                throw new InvalidNumberException("Введите число в пределах [1, 2]");
            }
        } else {
            throw new InvalidNumberException("Выбор должен являться числом");
        }
    }

    public int sizeCheck(String length) throws InvalidNumberException {
        if (length.matches(intMatching)) {
            int integerLength = Integer.parseInt(length);
            if (integerLength >= mazeSizeMinimum && integerLength <= mazeSizeMaximum) {
                return integerLength / 2 + 1;
            } else {
                throw new InvalidNumberException("Размерам следует быть в пределах [2, 35]");
            }
        } else {
            throw new InvalidNumberException("Размерами лабиринта должны являться числа");
        }
    }

    public int pointHeightCheck(String pointHeightCheck, int height) throws InvalidNumberException {
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

    public int pointWidthCheck(String pointWidthCheck, int width) throws InvalidNumberException {
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

    public int coinsAmountCheck(String coinsAmount, int passagesAmount) throws InvalidCoinsAmount {
        if (coinsAmount.matches(intMatching)) {
            int integerCoinsAmount = Integer.parseInt(coinsAmount);
            if (integerCoinsAmount <= passagesAmount && integerCoinsAmount >= 0) {
                return integerCoinsAmount;
            } else {
                throw new InvalidCoinsAmount("Количество монет не может быть больше количества свободных ячеек");
            }
        } else {
            throw new InvalidCoinsAmount("Количество монеток должно являться числом");
        }
    }
}
