package pp.pwr;

import pp.pwr.algorithms.BackTracking;
import pp.pwr.algorithms.ConstraintSatisfactionProblem;
import pp.pwr.algorithms.ForwardChecking;
import pp.pwr.models.FutoshikiModel;

public class Main {

    public static void main(String[] args) {
	    String path = "pp/pwr/Data/Research/test_futo_7_0.txt";

	    FutoshikiModel futoshikiModel = new FutoshikiModel(path);
        ConstraintSatisfactionProblem bt = new BackTracking(futoshikiModel);
        ConstraintSatisfactionProblem fc = new ForwardChecking<>(futoshikiModel);
        bt.run();
        System.out.println(bt.getInfo());
        fc.run();
        System.out.println(fc.getInfo());
    }
}
