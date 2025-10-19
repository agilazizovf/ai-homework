package homework1.problem2;

import java.util.List;
import java.util.Map;

public interface Constraint {
    boolean isSatisfied(Map<Variable, String> assignment);
    List<Variable> getScope(); // hansı dəyişənləri əhatə edir
}

