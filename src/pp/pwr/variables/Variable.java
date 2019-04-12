package pp.pwr.variables;

import pp.pwr.constraints.ConstraintInterface;
import pp.pwr.domains.Domain;

import java.util.List;
import java.util.Set;

public abstract class Variable<T extends Comparable<T>> {

    T value;

    private Domain domain;
    private boolean predefined;
    private List<ConstraintInterface> constraints;
    private Set<Variable> constrainedVariables;

    public Variable(T value, Domain domain, boolean predefined) {
        this.value = value;
        this.domain = domain;
        this.predefined = predefined;
    }

    public Variable(T value, Domain domain) {
        this(value, domain, false);
    }

    public abstract String getName();

    public boolean isPredefined() {
        return predefined;
    }

    public Domain getDomain() {
        return domain;
    }

    public T getValue() {
        return value;
    }

    public void update(T value) {
        this.value = value;
    }


    public void appendConstraint(ConstraintInterface constraint) {
        constraints.add(constraint);
    }

    public void appendConstrainedVariables(List<Variable> variable) {
        constrainedVariables.addAll(variable);
    }

    public boolean validate() {
        ConstraintInterface constraint;
        for(int i = 0; i < constraints.size(); i++) {
            constraint = constraints.get(i);
            if(!constraint.check()) {
                return false;
            }
        }

        return true;
    }

    public Set getConstrainedVariables() {
        return constrainedVariables;
    }

    public boolean equals(Variable other) {
        return value.equals(other.value);
    }
}
