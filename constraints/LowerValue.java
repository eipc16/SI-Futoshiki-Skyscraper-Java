package pp.pwr.constraints;

import pp.pwr.variables.Variable;

import java.util.ArrayList;
import java.util.List;

public class LowerValue<T extends Comparable<T>> implements ConstraintInterface<T> {

    private Variable<T> parentVariable;
    private Variable<T> constrainedVariable;
    T defaultValue;

    public LowerValue(Variable<T> parentVariable, Variable<T> constrainedVariable, T defaultValue) {
        this.parentVariable = parentVariable;
        this.constrainedVariable = constrainedVariable;
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean check() {
        if(parentVariable.getValue().equals(defaultValue) || constrainedVariable.getValue().equals(defaultValue)) {
            return true;
        }

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
