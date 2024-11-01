package backend.academy;

import backend.academy.exceptions.InvalidCoinsAmount;
import backend.academy.exceptions.InvalidNumberException;

public class DataValidator {
    private static final String INT_MATCHING = "-?\\d+";
    private static final int MAZE_SIZE_MAXIMUM = 100;
    private static final int MAZE_SIZE_MINIMUM = 2;
    static final int COINS_MAZE_CAPACITY = 7;

    public int choiceCheck(String choice) throws InvalidNumberException {
        if (choice.matches(INT_MATCHING)) {
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
        if (!length.matches(INT_MATCHING)) {
            throw new InvalidNumberException("Размерами лабиринта должны являться числа");
        }

        int integerLength = Integer.parseInt(length);
        if (integerLength < MAZE_SIZE_MINIMUM || integerLength > MAZE_SIZE_MAXIMUM) {
            throw new InvalidNumberException(
                "Размерам следует быть в пределах [" + MAZE_SIZE_MINIMUM + ", " + MAZE_SIZE_MAXIMUM + "]");
        }

        return integerLength / 2 + 1;
    }

    public int pointHeightCheck(String pointHeightCheck, int height) throws InvalidNumberException {
        if (pointHeightCheck.matches(INT_MATCHING)) {
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
        if (pointWidthCheck.matches(INT_MATCHING)) {
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

    public int coinsAmountCheck(String coinsAmount) throws InvalidCoinsAmount {
        if (coinsAmount.matches(INT_MATCHING)) {
            int integerCoinsAmount = Integer.parseInt(coinsAmount);
            if (integerCoinsAmount <= COINS_MAZE_CAPACITY && integerCoinsAmount >= 0) {
                return integerCoinsAmount;
            } else {
                throw new InvalidCoinsAmount("Количество монет не может быть больше 7");
            }
        } else {
            throw new InvalidCoinsAmount("Количество монеток должно являться числом");
        }
    }
}
