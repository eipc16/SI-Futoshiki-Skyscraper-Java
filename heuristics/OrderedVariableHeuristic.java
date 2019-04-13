package pp.pwr.heuristics;

import pp.pwr.models.Model;
import pp.pwr.variables.Variable;

import java.util.List;

public class OrderedVariableHeuristic<T extends Comparable<T>> extends VariableHeuristic<T> {

    public Integer position;

    public OrderedVariableHeuristic(Model<T> model) {
        super(model);
        this.position = 0;
    }

    @Override
    public List<Variable<T>> getVariables() {
        return super.variablesToCheck;
    }

}
