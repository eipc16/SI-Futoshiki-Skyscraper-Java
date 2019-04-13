package pp.pwr.algorithms;

import pp.pwr.heuristics.VariableHeuristic;
import pp.pwr.models.Model;
import pp.pwr.variables.Variable;

public class BackTracking extends ConstraintSatisfactionProblem {
    public BackTracking(Model model, VariableHeuristic variableHeuristic, boolean htmlDump) {
        super(model, variableHeuristic, "backtracking", htmlDump);
    }

    @Override
    boolean validate(Variable variable) {
        return variable.validate();
    }
}
