package pp.pwr.heuristics.ValueHeuristics;

import pp.pwr.variables.Variable;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LeastConstrainingValueHeuristic<T extends Comparable<T>> extends ValueHeuristic<T> {


    @Override
    public List<T> getDomain(Variable<T> variable) {
        List<T> domain = variable.getDomain();

        return domain.stream().sorted(Comparator.comparing(v ->this.countPossibleChildrenValues(variable, v), Comparator.reverseOrder())).collect(Collectors.toList());
    }

    public int countPossibleChildrenValues(Variable<T> variable, T value) {
        int count = 0;
        T startValue = variable.getValue();
        variable.update(value);

        for(Variable<T> child: variable.getConstrainedVariables()) {
            count += super.countPossibleValues(child);
        }

        variable.update(startValue);
        return count;
    }
}
