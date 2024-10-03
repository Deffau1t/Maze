package backend.academy;

import backend.academy.models.Maze;

public interface Generator {
    Maze generate(int height, int width);
}
