package backend.academy;

import backend.academy.models.Maze;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        Maze maze = menu.mazeGeneration();
        menu.mazeSolving(maze);
    }
}
