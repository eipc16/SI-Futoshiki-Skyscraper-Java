package pp.pwr.variables;

import pp.pwr.constraints.ConstraintInterface;

import java.util.*;

public abstract class Variable<T extends Comparable<T>> {

    T value;

    private boolean predefined;

    private List<T> domain;

    private List<ConstraintInterface> constraints;
    private Set<Variable<T>> constrainedVariables;

    private List<Variable<T>> rowVariables;
    private List<Variable<T>> columnVariables;

    public Variable(T value, boolean predefined) {
        this.value = value;
        this.predefined = predefined;

        this.constraints = new ArrayList<>();
        this.constrainedVariables = new HashSet<>();

        this.rowVariables = new ArrayList<>();
        this.columnVariables = new ArrayList<>();

        this.domain = new ArrayList<>();
    }

    public List<T> getDomain() {
        return this.domain;
    }

    public boolean removePossibleValue(T value) {
        if(predefined) {
            return false;
        }

        return domain.remove(value);
    }

    public boolean addPossibleValue(T value) {
        if(predefined) {
            return false;
        }

        return domain.add(value);
    }

    public void setPredefined() {
        this.predefined = true;
    }

    public T getMaxPossible() {
        try {
            return domain.stream().max(T::compareTo).orElseThrow(NoSuchFieldException::new);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setDomain(List<T> domain) {
        this.domain = domain;
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

    public void appendConstraint(ConstraintInterface constraint) {
        constraints.add(constraint);
    }

    public void appendConstrainedVariables(List<Variable<T>> variables) {
        constrainedVariables.addAll(variables);
    }

    public void appendConstrainedVariable(Variable<T> variable) {
        constrainedVariables.add(variable);
    }

    public void setRowVariables(ArrayList<Variable<T>> rowVariables) {
        this.rowVariables = rowVariables;
    }

    public void setColumnVariables(ArrayList<Variable<T>> columnVariables) {
        this.columnVariables = columnVariables;
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

    public int countConstrainedVariablesEqualsDefault(T defaultValue) {
        int count = 0;
        for(Variable<T> v: constrainedVariables) {
            if(v.getValue().equals(defaultValue)) {
                count++;
            }
        }
        return count;
    }

    public boolean sameValue(Variable other) {
        return value.equals(other.value);
    }
}
