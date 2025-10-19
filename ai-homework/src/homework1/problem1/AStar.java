package homework1.problem1;

import java.util.*;

public class AStar {
    private final Graph graph;
    private final Heuristic heuristic;

    public AStar(Graph graph, Heuristic heuristic) {
        this.graph = graph;
        this.heuristic = heuristic;
    }

    public Result findPath(String start, String goal) {
        Map<String, Double> gScore = new HashMap<>();
        Map<String, Double> fScore = new HashMap<>();
        Map<String, String> cameFrom = new HashMap<>();
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::getFScore));
        Set<String> closedSet = new HashSet<>();
        int expandedNodes = 0;

        for (String node : graph.getNodes()) {
            gScore.put(node, Double.POSITIVE_INFINITY);
            fScore.put(node, Double.POSITIVE_INFINITY);
        }

        gScore.put(start, 0.0);
        fScore.put(start, heuristic.calculate(start, goal));
        openSet.add(new Node(start, fScore.get(start)));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            expandedNodes++;

            if (current.getName().equals(goal)) {
                return new Result(reconstructPath(cameFrom, current.getName()), gScore.get(goal), expandedNodes);
            }

            closedSet.add(current.getName());

            for (Edge edge : graph.getNeighbors(current.getName())) {
                if (closedSet.contains(edge.getTarget())) continue;

                double tentativeG = gScore.get(current.getName()) + edge.getCost();
                if (tentativeG < gScore.get(edge.getTarget())) {
                    cameFrom.put(edge.getTarget(), current.getName());
                    gScore.put(edge.getTarget(), tentativeG);
                    double f = tentativeG + heuristic.calculate(edge.getTarget(), goal);
                    fScore.put(edge.getTarget(), f);
                    openSet.add(new Node(edge.getTarget(), f));
                }
            }
        }

        return new Result(Collections.emptyList(), Double.POSITIVE_INFINITY, expandedNodes);
    }

    private List<String> reconstructPath(Map<String, String> cameFrom, String current) {
        LinkedList<String> path = new LinkedList<>();
        path.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.addFirst(current);
        }
        return path;
    }
}
