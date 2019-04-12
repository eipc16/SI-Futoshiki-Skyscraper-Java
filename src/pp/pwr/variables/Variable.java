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
        return this.predefined;
    }

    public Domain getDomain() {
        return this.domain;
    }

    public T getValue() {
        return this.value;
    }

    public void update(T value) {
        this.value = value;
    }


    public void appendConstraint(ConstraintInterface constraint) {
        this.constraints.add(constraint);
    }

    public void appendConstrainedVariables(List<Variable> variable) {
        this.constrainedVariables.addAll(variable);
    }

    public boolean validate() {
        ConstraintInterface constraint;
        for(int i = 0; i < this.constraints.size(); i++) {
            constraint = this.constraints.get(i);
            if(!constraint.check()) {
                return false;
            }
        }

        return true;
    }

    public Set getConstrainedVariables() {
        return this.constrainedVariables;
    }
}
