package homework1.problem1;

public class Edge {
    private final String target;
    private final double cost;

    public Edge(String target, double cost) {
        this.target = target;
        this.cost = cost;
    }

    public String getTarget() {
        return target;
    }

    public double getCost() {
        return cost;
    }
}
