package homework1.problem2;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Variable WA = new Variable("WA", new HashSet<>(Arrays.asList("Red", "Green", "Blue")));
        Variable NT = new Variable("NT", new HashSet<>(Arrays.asList("Red", "Green", "Blue")));
        Variable SA = new Variable("SA", new HashSet<>(Arrays.asList("Red", "Green", "Blue")));
        Variable Q = new Variable("Q", new HashSet<>(Arrays.asList("Red", "Green", "Blue")));
        Variable NSW = new Variable("NSW", new HashSet<>(Arrays.asList("Red", "Green", "Blue")));
        Variable V = new Variable("V", new HashSet<>(Arrays.asList("Red", "Green", "Blue")));
        Variable T = new Variable("T", new HashSet<>(Arrays.asList("Red", "Green", "Blue")));

        List<Variable> variables = Arrays.asList(WA, NT, SA, Q, NSW, V, T);

        List<Constraint> constraints = new ArrayList<>();

        constraints.add(new NotEqualConstraint(WA, NT));
        constraints.add(new NotEqualConstraint(WA, SA));
        constraints.add(new NotEqualConstraint(NT, SA));
        constraints.add(new NotEqualConstraint(NT, Q));
        constraints.add(new NotEqualConstraint(SA, Q));
        constraints.add(new NotEqualConstraint(SA, NSW));
        constraints.add(new NotEqualConstraint(SA, V));
        constraints.add(new NotEqualConstraint(Q, NSW));
        constraints.add(new NotEqualConstraint(NSW, V));

        CSP australiaCSP = new CSP(variables, constraints);

        // --- CSP-i həll et ---
        Map<Variable, String> solution = BacktrackingSolver.solve(australiaCSP);

        // --- Nəticəni çap et ---
        if (solution != null) {
            System.out.println("Solution found:");
            solution.forEach((var, val) -> System.out.println(var.getName() + " -> " + val));
        } else {
            System.out.println("No solution exists.");
        }
    }

    static class NotEqualConstraint implements Constraint {
        private Variable var1;
        private Variable var2;

        public NotEqualConstraint(Variable var1, Variable var2) {
            this.var1 = var1;
            this.var2 = var2;
        }

        @Override
        public boolean isSatisfied(Map<Variable, String> assignment) {
            if (!assignment.containsKey(var1) || !assignment.containsKey(var2)) return true;
            return !assignment.get(var1).equals(assignment.get(var2));
        }

        @Override
        public List<Variable> getScope() {
            return Arrays.asList(var1, var2);
        }
    }
}

