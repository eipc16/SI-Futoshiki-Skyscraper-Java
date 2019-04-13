package pp.pwr;

import pp.pwr.algorithms.BackTracking;
import pp.pwr.algorithms.ConstraintSatisfactionProblem;
import pp.pwr.algorithms.ForwardChecking;
import pp.pwr.heuristics.MostConstrainedVariableHeuristic;
import pp.pwr.heuristics.OrderedVariableHeuristic;
import pp.pwr.heuristics.VariableHeuristic;
import pp.pwr.models.FutoshikiModel;

public class Main {

    public static void main(String[] args) {
        String fileName = "test_futo_7_0";
	    String path = String.format("pp/pwr/Data/Research/%s.txt", fileName);

        Thread t1 = new Thread(() -> {
            System.out.println("Backtracking started!");
            FutoshikiModel model = new FutoshikiModel(path, fileName);
            //VariableHeuristic heuristic = new OrderedVariableHeuristic<>(model);
            VariableHeuristic heuristic = new OrderedVariableHeuristic<>(model);
            ConstraintSatisfactionProblem bt = new BackTracking(model, heuristic, false);
            bt.run();
            System.out.println(String.format("Results for backtracking:\n%s", bt.getInfo()));
        });

        Thread t2 = new Thread(() -> {
            System.out.println("Forwardchecking started!");
            FutoshikiModel model2 = new FutoshikiModel(path, fileName);
            //VariableHeuristic heuristic2 = new OrderedVariableHeuristic<>(model2);
            VariableHeuristic heuristic2 = new MostConstrainedVariableHeuristic<>(model2);
            ConstraintSatisfactionProblem fc = new BackTracking(model2, heuristic2, true);
            fc.run();
            System.out.println(String.format("Results for forwardchecking:\n%s", fc.getInfo()));
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
