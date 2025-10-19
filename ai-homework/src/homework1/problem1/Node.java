package homework1.problem1;

public class Node {
    private final String name;
    private final double fScore;

    public Node(String name, double fScore) {
        this.name = name;
        this.fScore = fScore;
    }

    public String getName() {
        return name;
    }

    public double getFScore() {
        return fScore;
    }
}
