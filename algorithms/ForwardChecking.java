package pp.pwr.algorithms;

import pp.pwr.models.Model;
import pp.pwr.variables.Variable;

public class ForwardChecking<T extends Comparable<T>> extends ConstraintSatisfactionProblem<T> {
    public ForwardChecking(Model<T> model) {
        super(model);
    }

    public boolean hasPossibleValue(Variable<T> variable) {
        boolean validValue;

        for(T value: model.getDomain()) {
            variable.update(value);

            validValue = variable.validate();

            if(validValue) {
                variable.update(defaultValue);
                return true;
            }
        }
        variable.update(defaultValue);
        return false;
    }

    @Override
    public boolean validate(Variable<T> variable) {
        if(!variable.validate()) {
            return false;
        }

        for(Variable<T> v: variable.getConstrainedVariables()) {
            if(v.getValue().equals(defaultValue) && !hasPossibleValue(v)) {
                return false;
            }
        }

        return true;
    }
}
