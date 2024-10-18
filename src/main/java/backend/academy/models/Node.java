package backend.academy.models;

public class Node {
    public Coordinate position;
    public Node parent;
    public int g; // стоимость пути от начала до текущей позиции
    public int f; // оценка общей стоимости (g + h)

    public Node(Coordinate position, Node parent, int g, int h) {
        this.position = position;
        this.parent = parent;
        this.g = g;
        this.f = g + h;
    }
}
