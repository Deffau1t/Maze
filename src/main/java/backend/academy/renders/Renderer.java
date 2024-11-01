package backend.academy.renders;

import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import java.util.List;

public interface Renderer {
    String renderMazeWithCoordinates(Maze maze, Coordinate start, Coordinate end);

    String renderMazeWithPath(Maze maze, List<Coordinate> path);
}
