package homework1.problem2;

import java.util.*;

public class AC3 {

    public static boolean apply(CSP csp) {
        Queue<Pair<Variable, Variable>> queue = new LinkedList<>();

        for (Constraint constraint : csp.getConstraints()) {
            List<Variable> scope = constraint.getScope();
            if (scope.size() == 2) {
                queue.add(new Pair<>(scope.get(0), scope.get(1)));
                queue.add(new Pair<>(scope.get(1), scope.get(0)));
            }
        }

        while (!queue.isEmpty()) {
            Pair<Variable, Variable> pair = queue.poll();
            if (revise(pair.getKey(), pair.getValue(), csp)) {
                if (pair.getKey().getDomain().isEmpty()) {
                    return false;
                }
                for (Constraint c : csp.getConstraintsFor(pair.getKey())) {
                    for (Variable neighbor : c.getScope()) {
                        if (!neighbor.equals(pair.getKey())) {
                            queue.add(new Pair<>(neighbor, pair.getKey()));
                        }
                    }
                }
            }
        }
        return true;
    }

    private static boolean revise(Variable xi, Variable xj, CSP csp) {
        boolean revised = false;
        Set<String> toRemove = new HashSet<>();
        for (String valXi : xi.getDomain()) {
            boolean satisfied = false;
            for (String valXj : xj.getDomain()) {
                Map<Variable, String> tempAssign = new HashMap<>();
                tempAssign.put(xi, valXi);
                tempAssign.put(xj, valXj);
                for (Constraint c : csp.getConstraintsFor(xi)) {
                    if (c.isSatisfied(tempAssign)) {
                        satisfied = true;
                        break;
                    }
                }
            }
            if (!satisfied) {
                toRemove.add(valXi);
                revised = true;
            }
        }
        xi.getDomain().removeAll(toRemove);
        return revised;
    }

    private static class Pair<K,V> {
        private K key;
        private V value;
        public Pair(K k, V v){ key = k; value=v; }
        public K getKey(){return key;}
        public V getValue(){return value;}
    }
}

