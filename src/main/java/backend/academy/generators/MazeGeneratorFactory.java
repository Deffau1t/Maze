package backend.academy.generators;

import backend.academy.models.GeneratorType;

public class MazeGeneratorFactory {

    private MazeGeneratorFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static Generator createGenerator(int correctSolvingChoice) {
        GeneratorType generatorType = GeneratorType.fromValue(correctSolvingChoice);
        return switch (generatorType) {
            case KRUSKAL -> new KruskalMazeGenerator();
            case RECURSIVEBACKTRACKER -> new RecursiveBacktrackerMazeGenerator();
            default -> throw new IllegalArgumentException("Неизвестный тип лабиринта: " + generatorType);
        };
    }
}
