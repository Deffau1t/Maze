package backend.academy.models;

public class Edge {
    public Coordinate from;
    public Coordinate to;

    public Edge(Coordinate from, Coordinate to) {
        this.from = from;
        this.to = to;
    }
}
