package pp.pwr.algorithms;

import pp.pwr.models.Model;
import pp.pwr.variables.Variable;

public class BackTracking extends ConstraintSatisfactionProblem {
    public BackTracking(Model model, boolean htmlDump) {
        super(model, "backtracking", htmlDump);
    }

    @Override
    boolean validate(Variable variable) {
        return variable.validate();
    }
}
