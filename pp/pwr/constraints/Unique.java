package pp.pwr.constraints;

import pp.pwr.variables.Variable;
import java.util.List;
import java.util.stream.Collectors;

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

        for(int i = 0; i < constrainedVariables.size(); i++) {
            if (constrainedVariables.get(i).sameValue(parentVariable)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public List<Variable<T>> getConstrained() {
        return this.constrainedVariables;
    }

    @Override
    public String toString() {
        return String.format("%s unique in %s", parentVariable.getValue().toString(), constrainedVariables.stream().map(x -> x.getValue().toString()).collect(Collectors.joining(" ")));
    }
}
