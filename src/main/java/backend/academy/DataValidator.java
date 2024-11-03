package backend.academy;

import backend.academy.exceptions.InvalidCoinsAmount;

public class DataValidator {
    private static final String INT_MATCHING = "-?\\d+";
    private static final int MAZE_SIZE_MAXIMUM = 100;
    private static final int MAZE_SIZE_MINIMUM = 3;
    static final int COINS_MAZE_CAPACITY = 7;
    static final double COINS_MAZE_MAX_PERCENTAGE = 0.6;
    public static final int FIRST_ERROR_STATUS = -1;
    public static final int SECOND_ERROR_STATUS = -2;

    public int choiceCheck(String choice) {
        if (choice.matches(INT_MATCHING)) {
            int integerChoice = Integer.parseInt(choice);
            if (integerChoice >= 1 && integerChoice <= 2) {
                return 0;
            } else {
                return FIRST_ERROR_STATUS;
            }
        } else {
            return SECOND_ERROR_STATUS;
        }
    }

    public int sizeCheck(String length) {
        if (!length.matches(INT_MATCHING)) {
            return FIRST_ERROR_STATUS;
        }

        int integerLength = Integer.parseInt(length);
        if (integerLength < MAZE_SIZE_MINIMUM || integerLength > MAZE_SIZE_MAXIMUM) {
            return SECOND_ERROR_STATUS;
        }

        return integerLength / 2 + 1;
    }

    public int pointHeightCheck(String pointHeightCheck, int height) {
        if (pointHeightCheck.matches(INT_MATCHING)) {
            int integerPointHeightCheck = Integer.parseInt(pointHeightCheck);
            if (integerPointHeightCheck >= 1 && integerPointHeightCheck < height) {
                return integerPointHeightCheck;
            } else {
                return FIRST_ERROR_STATUS;
            }
        } else {
            return SECOND_ERROR_STATUS;
        }
    }

    public int pointWidthCheck(String pointWidthCheck, int width) {
        if (pointWidthCheck.matches(INT_MATCHING)) {
            int integerPointWidthCheck = Integer.parseInt(pointWidthCheck);
            if (integerPointWidthCheck >= 1 && integerPointWidthCheck < width) {
                return integerPointWidthCheck;
            } else {
                return FIRST_ERROR_STATUS;
            }
        } else {
            return SECOND_ERROR_STATUS;
        }
    }

    public int coinsAmountCheck(String coinsAmount, int passagesAmount) throws InvalidCoinsAmount {
        if (coinsAmount.matches(INT_MATCHING)) {
            int integerCoinsAmount = Integer.parseInt(coinsAmount);
            if (integerCoinsAmount <= COINS_MAZE_CAPACITY && integerCoinsAmount >= 0
                && integerCoinsAmount <= passagesAmount * COINS_MAZE_MAX_PERCENTAGE) {
                return integerCoinsAmount;
            } else {
                throw new InvalidCoinsAmount("Количество монет не может быть больше "
                    + Math.min(COINS_MAZE_CAPACITY, Math.round(passagesAmount * COINS_MAZE_MAX_PERCENTAGE)));
            }
        } else {
            throw new InvalidCoinsAmount("Количество монеток должно являться числом");
        }
    }
}
