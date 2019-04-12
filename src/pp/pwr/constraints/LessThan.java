package pp.pwr.constraints;

import pp.pwr.variables.Variable;

import java.util.ArrayList;
import java.util.List;

public class LessThan<T extends Comparable<T>> implements ConstraintInterface<T> {

    private Variable<T> parentVariable;
    private Variable<T> constrainedVariable;

    public LessThan(Variable parentVariable, Variable constrainedVariable) {
        this.parentVariable = parentVariable;
        this.constrainedVariable = constrainedVariable;
    }

    @Override
    public boolean check() {
        int compare = parentVariable.getValue().compareTo(constrainedVariable.getValue());
        return compare < 0;
    }

    @Override
    public List<Variable<T>> getConstrained() {
        List<Variable<T>> result = new ArrayList<>();
        result.add(constrainedVariable);
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s < %s", parentVariable.getName(), constrainedVariable.getName());
    }
}
