package pp.pwr.algorithms;

import pp.pwr.heuristics.ValueHeuristics.ValueHeuristic;
import pp.pwr.heuristics.VariableHeuristics.VariableHeuristic;
import pp.pwr.models.Model;
import pp.pwr.variables.Variable;

public class BackTracking extends ConstraintSatisfactionProblem {
    public BackTracking(Model model, VariableHeuristic variableHeuristic, ValueHeuristic valueHeuristic, boolean htmlDump) {
        super(model, variableHeuristic, valueHeuristic, "backtracking", htmlDump);
    }

    @Override
    boolean validate(Variable variable) {
        return variable.validate();
    }
}
