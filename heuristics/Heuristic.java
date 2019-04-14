package pp.pwr.heuristics;

import pp.pwr.variables.Variable;

public abstract class Heuristic<T extends Comparable<T>> {

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
}
