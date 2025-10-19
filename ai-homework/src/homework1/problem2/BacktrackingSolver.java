package homework1.problem2;

import java.util.*;

public class BacktrackingSolver {

    public static Map<Variable, String> solve(CSP csp) {
        AC3.apply(csp); // arc-consistency
        return backtrack(new HashMap<>(), csp);
    }

    private static Map<Variable, String> backtrack(Map<Variable, String> assignment, CSP csp) {
        if (assignment.size() == csp.getVariables().size()) return assignment;

        Variable var = selectUnassignedVariable(csp, assignment); // MRV
        List<String> values = orderDomainValues(var, assignment, csp); // LCV

        for (String value : values) {
            assignment.put(var, value);
            if (isConsistent(var, assignment, csp)) {
                Map<Variable, String> result = backtrack(assignment, csp);
                if (result != null) return result;
            }
            assignment.remove(var);
        }
        return null;
    }

    private static Variable selectUnassignedVariable(CSP csp, Map<Variable, String> assignment) {
        // MRV: minimum remaining values
        return csp.getVariables().stream()
                .filter(v -> !assignment.containsKey(v))
                .min(Comparator.comparingInt(v -> v.getDomain().size()))
                .orElse(null);
    }

    private static List<String> orderDomainValues(Variable var, Map<Variable, String> assignment, CSP csp) {
        // LCV: sort values that eliminate fewest options for neighbors
        List<String> values = new ArrayList<>(var.getDomain());
        values.sort(Comparator.comparingInt(val -> countConstraints(val, var, assignment, csp)));
        return values;
    }

    private static int countConstraints(String val, Variable var, Map<Variable, String> assignment, CSP csp) {
        int count = 0;
        assignment.put(var, val);
        for (Constraint c : csp.getConstraintsFor(var)) {
            for (Variable neighbor : c.getScope()) {
                if (!assignment.containsKey(neighbor)) {
                    count += neighbor.getDomain().size();
                }
            }
        }
        assignment.remove(var);
        return count;
    }

    private static boolean isConsistent(Variable var, Map<Variable, String> assignment, CSP csp) {
        for (Constraint c : csp.getConstraintsFor(var)) {
            if (!c.isSatisfied(assignment)) return false;
        }
        return true;
    }
}

