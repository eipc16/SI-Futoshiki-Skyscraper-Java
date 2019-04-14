package pp.pwr.heuristics.ValueHeuristics;

import pp.pwr.heuristics.Heuristic;
import pp.pwr.models.Model;
import pp.pwr.variables.Variable;

import java.util.List;

public abstract class ValueHeuristic<T extends Comparable<T>> extends Heuristic<T> {

    public abstract List<T> getDomain(Variable<T> variable);
}
