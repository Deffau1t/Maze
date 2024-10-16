package backend.academy.exceptions;

import lombok.Getter;

@Getter
public class MazeException extends Exception {
    private final String message;

    public MazeException(String message) {
        this.message = message;
    }
}
