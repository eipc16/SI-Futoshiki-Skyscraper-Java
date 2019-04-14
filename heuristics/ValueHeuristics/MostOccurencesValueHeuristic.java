package pp.pwr.heuristics.ValueHeuristics;

import pp.pwr.variables.Variable;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MostOccurencesValueHeuristic<T extends Comparable<T>> extends ValueHeuristic<T> {

    @Override
    public List<T> getDomain(Variable<T> variable) {
        List<T> domain = variable.getDomain();

        return domain
                .stream()
                .sorted(Comparator.comparing(v -> this.countOccurencesInChilds(variable, v), Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    public int countOccurencesInChilds(Variable<T> variable, T value) {
        int count = 0;
        Set<Variable<T>> constrainedVariables = variable.getConstrainedVariables();

        for(Variable<T> child: constrainedVariables) {
            if(child.getDomain().contains(value)) {
                count++;
            }
        }

        return count;
    }
}
