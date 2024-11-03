package backend.academy.models;

import lombok.Getter;

@Getter
public enum GeneratorType {
    KRUSKAL(1),
    RECURSIVEBACKTRACKER(2);

    private final int value;

    GeneratorType(int value) {
        this.value = value;
    }

    public static GeneratorType fromValue(int value) {
        for (GeneratorType type : GeneratorType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Недопустимый тип лабиринта: " + value);
    }
}
