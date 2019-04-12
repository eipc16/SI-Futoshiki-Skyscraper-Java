package pp.pwr.models;

import pp.pwr.variables.Variable;
import java.util.List;

public class FutoshikiModel extends Model<Integer> {

    public FutoshikiModel(String filePath) {
        super(0);
        loadModel(filePath);
    }

    @Override
    void parseModel(List<String> data) {

        //loadingVariables
        

        //loadingConstraints

    }

    @Override
    public boolean validate() {
        boolean containsFalse = variableList.stream().map(Variable::validate).anyMatch(Boolean.FALSE::equals);
        return !containsFalse;
    }

    @Override
    public String getBoard() {
        return null;
    }
}
