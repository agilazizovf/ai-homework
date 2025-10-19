package homework1.problem1;

import java.io.*;
import java.util.*;

public class Graph {
    private final Map<String, List<Edge>> adjacencyList = new HashMap<>();

    public void addEdge(String source, String target, double cost) {
        adjacencyList.computeIfAbsent(source, k -> new ArrayList<>()).add(new Edge(target, cost));
        adjacencyList.computeIfAbsent(target, k -> new ArrayList<>()).add(new Edge(source, cost));
    }

    public List<Edge> getNeighbors(String node) {
        return adjacencyList.getOrDefault(node, new ArrayList<>());
    }

    public Set<String> getNodes() {
        return adjacencyList.keySet();
    }

    public static Graph loadFromFile(String path) throws IOException {
        Graph graph = new Graph();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.trim().split("\\s+");
                if (p.length == 3) {
                    graph.addEdge(p[0], p[1], Double.parseDouble(p[2]));
                }
            }
        }
        return graph;
    }
}
