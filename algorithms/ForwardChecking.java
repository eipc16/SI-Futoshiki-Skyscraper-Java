package pp.pwr.algorithms;

import pp.pwr.heuristics.VariableHeuristic;
import pp.pwr.models.Model;
import pp.pwr.variables.Variable;

import java.util.concurrent.atomic.AtomicBoolean;

public class ForwardChecking<T extends Comparable<T>> extends ConstraintSatisfactionProblem<T> {
    public ForwardChecking(Model<T> model, VariableHeuristic variableHeuristic, boolean htmlDump) {
        super(model, variableHeuristic, "forwardchecking", htmlDump);
    }

    public boolean hasPossibleValue(Variable<T> variable) {
        boolean validValue;

        if (variable.isPredefined()) {
            return true;
        }

        for(T value: variable.getDomain()) {
            variable.update(value);

            validValue = variable.validate();

            if (validValue) {
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
