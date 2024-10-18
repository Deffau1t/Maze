package backend.academy.generators;

import backend.academy.models.Cell;
import backend.academy.models.Coordinate;
import backend.academy.models.DisjointSet;
import backend.academy.models.Edge;
import backend.academy.models.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class KruskalMazeGenerator implements Generator {

    @Override
    public Maze generate(int height, int width) {
        List<Edge> edges = new ArrayList<>();
        DisjointSet ds = new DisjointSet(height * width);

        // Инициализация сетки
        Cell[][] grid = Generator.gridWallFilling(height, width);

        // Добавление всех возможных ребер
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (col < width - 1) {
                    edges.add(new Edge(new Coordinate(row, col), new Coordinate(row, col + 1)));
                }
                if (row < height - 1) {
                    edges.add(new Edge(new Coordinate(row, col), new Coordinate(row + 1, col)));
                }
            }
        }

        // Перемешиваем ребра случайным образом
        Collections.shuffle(edges);

        // Алгоритм Краскала
        for (Edge edge : edges) {
            Coordinate from = edge.from;
            Coordinate to = edge.to;

            int fromIndex = from.row() * width + from.col();
            int toIndex = to.row() * width + to.col();

            if (ds.union(fromIndex, toIndex)) {
                int wallRow = from.row() + to.row() + 1;
                int wallCol = from.col() + to.col() + 1;
                grid[wallRow][wallCol] = new Cell(wallRow, wallCol, Cell.Type.PASSAGE);
            }
        }

        return new Maze(height * 2 + 1, width * 2 + 1, grid);
    }
}
