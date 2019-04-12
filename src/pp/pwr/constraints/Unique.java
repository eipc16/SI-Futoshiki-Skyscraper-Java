package pp.pwr.constraints;

import pp.pwr.variables.Variable;
import java.util.List;

public class Unique<T extends Comparable<T>> implements ConstraintInterface<T> {

    Variable<T> parentVariable;
    List<Variable<T>> constrainedVariables;
    T defaultValue;

    public Unique(Variable<T> parentVariable, List<Variable<T>> constrainedVariables, T defaultValue) {
        this.parentVariable = parentVariable;
        this.constrainedVariables = constrainedVariables;
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean check() {
        if (parentVariable.getValue().equals(defaultValue)) {
            return true;
        }

        return this.constrainedVariables.stream().filter(x -> x.equals(parentVariable)).count() < 2;
    }

    @Override
    public List<Variable<T>> getConstrained() {
        return this.constrainedVariables;
    }

    @Override
    public String toString() {
        return String.format("%s unique in %s", parentVariable.getName(), constrainedVariables.toString());
    }
}
