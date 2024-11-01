package backend.academy.models;

import lombok.Getter;

@Getter
public enum SolverType {
    BFS(1),
    ASTAR(2);

    private final int value;

    SolverType(int value) {
        this.value = value;
    }

    public static SolverType fromValue(int value) {
        for (SolverType type : SolverType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Недопустимый тип решения: " + value);
    }
}
