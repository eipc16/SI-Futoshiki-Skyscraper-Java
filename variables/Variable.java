package pp.pwr.variables;

import pp.pwr.constraints.ConstraintInterface;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Variable<T extends Comparable<T>> {

    T value;

    private boolean predefined;

    private List<ConstraintInterface> constraints;
    private Set<Variable<T>> constrainedVariables;

    public Variable(T value, boolean predefined) {
        this.value = value;
        this.predefined = predefined;

        this.constraints = new ArrayList<>();
        this.constrainedVariables = new HashSet<>();

    }

    public Variable(T value, Set<T> domain) {
        this(value, false);
    }

    public abstract String getName();

    public boolean isPredefined() {
        return predefined;
    }

    public T getValue() {
        return value;
    }

    public void update(T value) {
        this.value = value;
    }

    public void appendConstraint(ConstraintInterface<T> constraint) {
        constraints.add(constraint);
        constrainedVariables.addAll(constraint.getConstrained());
    }

    public Set<Variable<T>> getConstrainedVariables() {
        return constrainedVariables;
    }

    public boolean validate() {
        for (ConstraintInterface cons: constraints) {
            if(!cons.check()) {
                return false;
            }
        }

        return true;
    }

    public boolean sameValue(Variable other) {
        return value.equals(other.value);
    }
}
