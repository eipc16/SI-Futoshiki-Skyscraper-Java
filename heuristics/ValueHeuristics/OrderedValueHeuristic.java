package pp.pwr.heuristics.ValueHeuristics;

import pp.pwr.variables.Variable;

import java.util.List;

public class OrderedValueHeuristic<T extends Comparable<T>> extends ValueHeuristic<T> {

    @Override
    public List<T> getDomain(Variable<T> variable) {
        return variable.getDomain();
    }
}
