package pp.pwr.constraints;

import pp.pwr.variables.Variable;

import java.util.List;

public interface ConstraintInterface<T extends Comparable<T>> {
    boolean check();

    List<Variable<T>> getConstrained();
}
