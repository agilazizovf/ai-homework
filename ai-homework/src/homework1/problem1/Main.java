package homework1.problem1;

public class Main {
    public static void main(String[] args) throws Exception {
        Graph graph = new Graph();
        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 2);
        graph.addEdge("A", "C", 4);
        graph.addEdge("C", "D", 1);
        graph.addEdge("B", "D", 5);

        Heuristic heuristic = new Heuristic();
        heuristic.addPosition("A", 0, 0);
        heuristic.addPosition("B", 1, 0);
        heuristic.addPosition("C", 1, 1);
        heuristic.addPosition("D", 2, 1);

        AStar aStar = new AStar(graph, heuristic);
        long start = System.currentTimeMillis();
        Result result = aStar.findPath("A", "D");
        long elapsed = System.currentTimeMillis() - start;

        System.out.println("=== A* RESULT ===");
        System.out.println("Path: " + result.getPath());
        System.out.println("Cost: " + result.getCost());
        System.out.println("Expanded Nodes: " + result.getExpandedNodes());
        System.out.println("Elapsed Time: " + elapsed + " ms");
    }
}
