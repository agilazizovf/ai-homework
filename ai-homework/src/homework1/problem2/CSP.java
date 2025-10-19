package homework1.problem2;

import java.util.*;

public class CSP {
    private List<Variable> variables;
    private List<Constraint> constraints;

    public CSP(List<Variable> variables, List<Constraint> constraints) {
        this.variables = variables;
        this.constraints = constraints;
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public List<Constraint> getConstraints() {
        return constraints;
    }

    public List<Constraint> getConstraintsFor(Variable var) {
        List<Constraint> related = new ArrayList<>();
        for (Constraint c : constraints) {
            if (c.getScope().contains(var)) {
                related.add(c);
            }
        }
        return related;
    }
}

