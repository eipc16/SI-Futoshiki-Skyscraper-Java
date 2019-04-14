package pp.pwr;

import pp.pwr.algorithms.BackTracking;
import pp.pwr.algorithms.ConstraintSatisfactionProblem;
import pp.pwr.algorithms.ForwardChecking;
import pp.pwr.heuristics.ValueHeuristics.LeastConstrainingValueHeuristic;
import pp.pwr.heuristics.ValueHeuristics.OrderedValueHeuristic;
import pp.pwr.heuristics.ValueHeuristics.ValueHeuristic;
import pp.pwr.heuristics.VariableHeuristics.MostConstrainedVariableHeuristic;
import pp.pwr.heuristics.VariableHeuristics.OrderedVariableHeuristic;
import pp.pwr.heuristics.VariableHeuristics.VariableHeuristic;
import pp.pwr.models.FutoshikiModel;
import pp.pwr.models.Model;

public class Main {

    public static void main(String[] args) {
        String fileName = args[0];
	    String path = String.format("pp/pwr/Data/Research/%s.txt", fileName);
        System.out.println(fileName + " working on Google Server :)");

        String method = args[1];

        Thread t1 = new Thread(() -> {
            //System.out.println("Backtracking started!");
            Model<Integer> model = new FutoshikiModel(path, fileName);
            //VariableHeuristic heuristic = new OrderedVariableHeuristic<>(model);
            VariableHeuristic<Integer> heuristic = new OrderedVariableHeuristic<>(model);
            ValueHeuristic<Integer> valueHeuristic = new OrderedValueHeuristic<>();
            ConstraintSatisfactionProblem bt;
            if(method.equals("forward")) {
                bt = new ForwardChecking<>(model, heuristic, valueHeuristic, false);
            } else {
                bt = new BackTracking(model, heuristic, valueHeuristic, false);
            }

            bt.run();
            System.out.println(String.format("Ordered heuristics:\n%s", bt.getInfo()));
        });

        Thread t2 = new Thread(() -> {
            //System.out.println("Forwardchecking started!");
            Model<Integer> model2 = new FutoshikiModel(path, fileName);
            //VariableHeuristic<Integer> heuristic2 = new OrderedVariableHeuristic<>(model2);
            VariableHeuristic<Integer> heuristic2 = new MostConstrainedVariableHeuristic<>(model2);
            //ValueHeuristic<Integer> valueHeuristic2 = new LeastConstrainingValueHeuristic<>();
            ValueHeuristic<Integer> valueHeuristic2 = new OrderedValueHeuristic<>();
            ConstraintSatisfactionProblem fc;
            if(method.equals("forward")) {
                System.out.println("Forward");
                fc = new ForwardChecking<>(model2, heuristic2, valueHeuristic2, true);
            } else {
                System.out.println("Backtracking");
                fc = new BackTracking(model2, heuristic2, valueHeuristic2, true);
            }
            fc.run();
            System.out.println(String.format("Most constrained variable:\n%s", fc.getInfo()));
        });

        t1.start();
        t2.start();
//
//        ConstraintSatisfactionProblem bt = new BackTracking(model);
//        ConstraintSatisfactionProblem fc = new ForwardChecking<>(model);
//        bt.run();
//        System.out.println(bt.getInfo());
//
//        fc.run();
//        System.out.println(fc.getInfo());
    }
}
