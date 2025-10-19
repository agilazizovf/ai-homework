package homework1.problem1;

import java.io.*;
import java.util.*;

public class Heuristic {
    private final Map<String, double[]> positions = new HashMap<>();

    public void addPosition(String node, double x, double y) {
        positions.put(node, new double[]{x, y});
    }

    public double calculate(String a, String b) {
        double[] p1 = positions.get(a);
        double[] p2 = positions.get(b);
        if (p1 == null || p2 == null) return 0;
        return Math.sqrt(Math.pow(p1[0] - p2[0], 2) + Math.pow(p1[1] - p2[1], 2));
    }

    public static Heuristic loadFromFile(String path) throws IOException {
        Heuristic h = new Heuristic();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.trim().split("\\s+");
                if (p.length == 3) {
                    h.addPosition(p[0], Double.parseDouble(p[1]), Double.parseDouble(p[2]));
                }
            }
        }
        return h;
    }
}
