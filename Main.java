package pp.pwr;

import pp.pwr.algorithms.BackTracking;
import pp.pwr.algorithms.ConstraintSatisfactionProblem;
import pp.pwr.algorithms.ForwardChecking;
import pp.pwr.models.FutoshikiModel;

public class Main {

    public static void main(String[] args) {
        String fileName = "test_futo_6_0";
	    String path = String.format("pp/pwr/Data/Research/%s.txt", fileName);

        Thread t1 = new Thread(() -> {
            System.out.println("Backtracking started!");
            FutoshikiModel model = new FutoshikiModel(path, fileName);
            ConstraintSatisfactionProblem bt = new BackTracking(model, false);
            bt.run();
            System.out.println(String.format("Results for backtracking:\n%s", bt.getInfo()));
        });

        Thread t2 = new Thread(() -> {
            FutoshikiModel model2 = new FutoshikiModel(path, fileName);
            System.out.println("Forwardchecking started!");
            ConstraintSatisfactionProblem fc = new ForwardChecking<>(model2, true);
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
