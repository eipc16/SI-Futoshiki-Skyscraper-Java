package pp.pwr.heuristics;

import pp.pwr.models.Model;
import pp.pwr.variables.Variable;

import java.util.Comparator;
import java.util.List;

public class MostConstrainedVariableHeuristic<T extends Comparable<T>> extends VariableHeuristic<T> {

    public MostConstrainedVariableHeuristic(Model<T> model) {
        super(model);
    }

    @Override
    public Variable<T> get(int i) {
//        super.variablesToCheck.stream().map(Variable::getValue).forEach(System.out::print);
//        System.out.println();
        return super.variablesToCheck
                .stream()
                .filter(v -> v.getValue().equals(model.getDefaultValue()))
                .min(Comparator.comparing(this::countPossibleValues))
                .orElseThrow(NoSuchFieldError::new);
    }

    public Integer countPossibleValues(Variable<T> variable) {
        int count = 0;

        T startValue = variable.getValue();

        for(T value: variable.getDomain()) {
            variable.update(value);

            if(variable.validate()) {
                count++;
            }
        }

        variable.update(startValue);

        return count;
    }

    @Override
    public List<Variable<T>> getVariables() {
        return null;
    }
}
