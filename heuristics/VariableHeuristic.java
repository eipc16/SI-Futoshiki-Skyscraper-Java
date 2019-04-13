package pp.pwr.heuristics;

import pp.pwr.models.Model;
import pp.pwr.variables.Variable;

import java.util.List;
import java.util.stream.Collectors;

public abstract class VariableHeuristic<T extends Comparable<T>> {

    List<Variable<T>> variablesToCheck;
    Model<T> model;

    public VariableHeuristic(Model<T> model) {
        this.variablesToCheck = model.getVariableList().stream().filter(x -> !x.isPredefined()).collect(Collectors.toList());
        this.model = model;
    }

    public Variable<T> get(int i) {
        return variablesToCheck.get(i);
    }

    public int size() {
        return variablesToCheck.size();
    }

    public abstract List<Variable<T>> getVariables();
}
