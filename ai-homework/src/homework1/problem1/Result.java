package homework1.problem1;

import java.util.List;

public class Result {
    private final List<String> path;
    private final double cost;
    private final int expandedNodes;

    public Result(List<String> path, double cost, int expandedNodes) {
        this.path = path;
        this.cost = cost;
        this.expandedNodes = expandedNodes;
    }

    public List<String> getPath() {
        return path;
    }

    public double getCost() {
        return cost;
    }

    public int getExpandedNodes() {
        return expandedNodes;
    }
}
