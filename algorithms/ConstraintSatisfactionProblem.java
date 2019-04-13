package pp.pwr.algorithms;

import pp.pwr.models.Model;
import pp.pwr.variables.Variable;

import java.util.List;
import java.util.stream.Collectors;

public class ConstraintSatisfactionProblem<T extends Comparable<T>> {
    protected Model<T> model;
    protected List<Variable<T>> variablesToCheck;
    protected int activeVariablesSize;
    protected T defaultValue;

    private double time;
    Long iterations, solutions, backtracks;

    private String label;
    private boolean htmlDump;

    public ConstraintSatisfactionProblem(Model<T> model, String label, boolean htmlDump) {
        this.model = model;
        this.variablesToCheck = model.getVariableList().stream().filter(x -> !x.isPredefined()).collect(Collectors.toList());
        this.activeVariablesSize = this.variablesToCheck.size();
        this.defaultValue = model.getDefaultValue();

        this.time = 0;
        this.iterations = 0L;
        this.backtracks = 0L;
        this.solutions = 0L;

        this.label = label;
        this.htmlDump = htmlDump;
    }

    private boolean solve(int depth) {

        //System.out.println(model.getBoard());
        this.iterations++;

        if (depth == activeVariablesSize) {
            return true;
        }


        Variable<T> variable = variablesToCheck.get(depth);
        boolean deadend = true;

        for(T value: variable.getDomain()) {
            variable.update(value);
            //this.iterations++;
            if (validate(variable)) {
                deadend = false;

                if (solve(depth + 1)) {
                    if(depth + 1 == activeVariablesSize) {
                        this.solutions++;

                        System.out.println("Found solution");
                        System.out.println(model.getBoard());
                        System.out.println(getInfo());

                        if(htmlDump) {
                            model.dumpHTML(label);
                        }

                        variable.update(model.getDefaultValue());
                    }
                }
            }
        }

        if(deadend) {
            this.backtracks++;
        }

        variable.update(model.getDefaultValue());
        return false;
    }

    public void run() {
        this.time = System.nanoTime();
        this.iterations = 0L;
        this.solutions = 0L;

        solve(0);
    }

    public String getInfo() {
        return String.format("Elapsed time: %5.2f | Iterations: %8d | Backtracks: %8d | Solutions: %4d", (System.nanoTime() - time) / 1000000000, iterations, backtracks, solutions);
    }

    boolean validate(Variable<T> variable) {
        return false;
    }
}
