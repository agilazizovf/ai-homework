package homework1.problem2;

import java.util.Set;

public class Variable {
    private String name;
    private Set<String> domain;

    public Variable(String name, Set<String> domain) {
        this.name = name;
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public Set<String> getDomain() {
        return domain;
    }

    public void setDomain(Set<String> domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return name;
    }
}
