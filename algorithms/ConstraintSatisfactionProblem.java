package pp.pwr.algorithms;

import pp.pwr.heuristics.ValueHeuristics.LeastConstrainingValueHeuristic;
import pp.pwr.heuristics.ValueHeuristics.ValueHeuristic;
import pp.pwr.heuristics.VariableHeuristics.VariableHeuristic;
import pp.pwr.models.Model;
import pp.pwr.variables.PuzzleVariable;
import pp.pwr.variables.Variable;

import java.util.List;

public class ConstraintSatisfactionProblem<T extends Comparable<T>> {
    protected Model<T> model;
    protected List<Variable<T>> variablesToCheck;
    protected int activeVariablesSize;
    protected T defaultValue;

    private VariableHeuristic<T> variableHeuristic;
    private ValueHeuristic<T> valueHeuristic;

    private double time;
    Long iterations, solutions, backtracks;

    private String label;
    private boolean htmlDump;

    public ConstraintSatisfactionProblem(Model<T> model, VariableHeuristic<T> variableHeuristic, ValueHeuristic<T> valueHeuristic, String label, boolean htmlDump) {
        this.model = model;
        //this.variablesToCheck = model.getVariableList().stream().filter(x -> !x.isPredefined()).collect(Collectors.toList());
        //this.variablesToCheck = variableHeuristic.getVariables();
        this.activeVariablesSize = variableHeuristic.size();
        this.defaultValue = model.getDefaultValue();

        this.variableHeuristic = variableHeuristic;
        this.valueHeuristic = valueHeuristic;

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

        Variable<T> variable = variableHeuristic.get(depth);
        List<T> variableDomain = valueHeuristic.getDomain(variable);

//        if (valueHeuristic instanceof LeastConstrainingValueHeuristic) {
//            System.out.println(String.format("Zmienna: %s | Domena: %s", variable.getName(), variableDomain.toString()));
//        }
        boolean deadend = true;

        for(T value: variableDomain) {
            variable.update(value);
            //this.iterations++;
            if (validate(variable)) {
                deadend = false;

                if (solve(depth + 1)) {
                    if(depth + 1 == activeVariablesSize) {
                        this.solutions++;

                        System.out.println(String.format("Found solution: [%s, %s]", variableHeuristic.getClass().getSimpleName(), valueHeuristic.getClass().getSimpleName()));
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
        return String.format("(%s, %s)\nElapsed time: %5.2f | Iterations: %8d | Deadends: %8d | Solutions: %4d", variableHeuristic.getClass().getSimpleName(), valueHeuristic.getClass().getSimpleName(), (System.nanoTime() - time) / 1000000000, iterations, backtracks, solutions);
    }

    boolean validate(Variable<T> variable) {
        return false;
    }
}
