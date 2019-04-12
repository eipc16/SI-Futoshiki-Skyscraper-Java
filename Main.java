package pp.pwr;

import pp.pwr.algorithms.BackTracking;
import pp.pwr.algorithms.ConstraintSatisfactionProblem;
import pp.pwr.algorithms.ForwardChecking;
import pp.pwr.models.SkyscraperModel;

public class Main {

    public static void main(String[] args) {
	    String path = "pp/pwr/Data/Research/test_sky_6_3.txt";

	    //FutoshikiModel model = new FutoshikiModel(path);
        SkyscraperModel model = new SkyscraperModel(path);

        ConstraintSatisfactionProblem bt = new BackTracking(model);
        ConstraintSatisfactionProblem fc = new ForwardChecking<>(model);
        bt.run();
        System.out.println(bt.getInfo());

        fc.run();
        System.out.println(fc.getInfo());
    }
}
