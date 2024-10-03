package backend.academy;

import backend.academy.models.Cell;
import backend.academy.models.Maze;
import java.io.PrintStream;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        PrimMazeGenerator primMazeGenerator = new PrimMazeGenerator();
        PrintStream out = System.out;
        final int MAZE_WIDTH = 10;
        final int MAZE_HEIGHT = 10;
        Maze primMaze = primMazeGenerator.generate(MAZE_HEIGHT, MAZE_WIDTH);
        for (int y = 0; y < primMaze.grid()[0].length; y++) {
            for (int x = 0; x < primMaze.grid().length; x++) {
                out.print(primMaze.grid()[x][y].type() == Cell.Type.WALL ? "#" : " ");
            }
            out.println();
        }
    }
}
