package pp.pwr.algorithms;

import pp.pwr.constraints.ConstraintInterface;
import pp.pwr.models.Model;
import pp.pwr.variables.Variable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ConstraintSatisfactionProblem<T extends Comparable<T>> {
    private Model<T> model;
    private List<Variable<T>> variablesToCheck;
    private int activeVariablesSize;
    private T defaultValue;

    private double time;
    private int iterations, solutions;

    public ConstraintSatisfactionProblem(Model<T> model) {
        this.model = model;
        this.variablesToCheck = model.getVariableList().stream().filter(x -> !x.isPredefined()).collect(Collectors.toList());
        this.activeVariablesSize = this.variablesToCheck.size();
        this.time = 0;
        this.iterations = 0;
        this.solutions = 0;
    }

    private boolean solve(int depth) {
        this.iterations += 1;

        if (depth == activeVariablesSize) {
            return true;
        }
        Variable<T> variable = variablesToCheck.get(depth);

        for(T value: model.getDomain()) {
            variable.update(value);

            if (validate(variable) && solve(depth + 1)) {
                if (depth + 1 == activeVariablesSize && model.validate()) {
                    System.out.println("Found solution");
                    this.solutions += 1;
                    System.out.println(model.getBoard());
                    System.out.println(getInfo());

                    variable.update(model.getDefaultValue());

                    return true;
                }
            }
        }

        variable.update(model.getDefaultValue());
        return false;
    }

    public void run() {
        this.time = System.nanoTime();
        this.iterations = 0;
        this.solutions = 0;

        solve(0);
    }

    public String getInfo() {
        return String.format("Elapsed time: %5.2f | Iterations: %8d | Solutions: %4d", (System.nanoTime() - time) / 1000000000, iterations, solutions);
    }

    boolean validate(Variable<T> variable) {
        return false;
    }
}
