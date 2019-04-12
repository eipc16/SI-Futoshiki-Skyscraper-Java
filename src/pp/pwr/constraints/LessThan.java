package pp.pwr.constraints;

import pp.pwr.variables.Variable;

import java.util.ArrayList;
import java.util.List;

public class LessThan implements ConstraintInterface {

    Variable parentVariable;
    Variable constrainedVariable;

    public LessThan(Variable parentVariable, Variable constrainedVariable) {
        this.parentVariable = parentVariable;
        this.constrainedVariable = constrainedVariable;
    }

    @Override
    public boolean check() {
        int compare = this.parentVariable.getValue().compareTo(this.constrainedVariable.getValue());
        return compare < 0;
    }

    @Override
    public List getConstrained() {
        List<Variable> result = new ArrayList<>();
        result.add(this.constrainedVariable);
        return result;
    }
}
