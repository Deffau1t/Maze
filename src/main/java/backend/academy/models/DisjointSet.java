package backend.academy.models;

public class DisjointSet {
    int[] parent;
    int[] rank;

    public DisjointSet(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    // Поиск корня множества
    int find(int elem) {
        if (parent[elem] != elem) {
            parent[elem] = find(parent[elem]);
        }
        return parent[elem];
    }

    // Объединение двух множеств
    public boolean connection(int firstPoint, int secondPoint) {
        int rootFirstPoint = find(firstPoint);
        int rootSecondPoint = find(secondPoint);

        if (rootFirstPoint != rootSecondPoint) {
            if (rank[rootFirstPoint] > rank[rootSecondPoint]) {
                parent[rootSecondPoint] = rootFirstPoint;
            } else if (rank[rootFirstPoint] < rank[rootSecondPoint]) {
                parent[rootFirstPoint] = rootSecondPoint;
            } else {
                parent[rootSecondPoint] = rootFirstPoint;
                rank[rootFirstPoint]++;
            }
            return true;
        }
        return false;
    }
}
