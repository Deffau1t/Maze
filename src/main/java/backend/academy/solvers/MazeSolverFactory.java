package backend.academy.solvers;

import backend.academy.models.SolverType;

public class MazeSolverFactory {

    private MazeSolverFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static Solver createSolver(int correctSolvingChoice) {
        SolverType solverType = SolverType.fromValue(correctSolvingChoice);
        return switch (solverType) {
            case BFS -> new BFSSolver();
            case ASTAR -> new AStarSolver();
            default -> throw new IllegalArgumentException("Неизвестный тип алгоритма: " + solverType);
        };
    }
}
