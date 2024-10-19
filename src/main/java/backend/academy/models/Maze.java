package backend.academy.models;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public final class Maze {
    private final int height;
    private final int width;
    private final Cell[][] grid;

    public Maze(int height, int width, Cell[][] grid) {
        this.height = height;
        this.width = width;
        this.grid = grid;
    }

    public static List<Coordinate> getPassageList(Maze maze) {
        List<Coordinate> passageList = new ArrayList<>();
        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                if (maze.grid()[row][col].type() == Cell.Type.PASSAGE) {
                    passageList.add(new Coordinate(row, col));
                }
            }
        }
        return passageList;
    }
}
