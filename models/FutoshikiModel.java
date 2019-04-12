package pp.pwr.models;

import pp.pwr.constraints.HigherValue;
import pp.pwr.constraints.LowerValue;
import pp.pwr.constraints.Unique;
import pp.pwr.variables.PuzzleVariable;
import pp.pwr.variables.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FutoshikiModel extends Model<Integer> {

    public FutoshikiModel(String filePath) {
        super(filePath,0);

        loadModel();
    }

    void loadVariables(List<String> data) {
        Integer value;
        boolean predefined;

        for(int i = 0; i < data.size(); i++ ) {
            String[] line = data.get(i).split(";");

            for(int j = 0; j < line.length; j++) {
                value = Integer.parseInt(line[j]);
                predefined = !value.equals(defaultValue);

                variableList.add(new PuzzleVariable(value, j, i, predefined));
            }
        }
    }

    void loadFutoshikiConstraints(List<String> data) {
        int lower_row, lower_col, higher_row, higher_col;

        for(int i = 0; i < data.size(); i++) {
            String lower, higher;
            String[] line = data.get(i).split(";");

            lower = line[0];
            higher = line[1];

            lower_row = ((int) line[0].charAt(0)) - 65;
            lower_col = ((int) line[0].charAt(1)) - 49;

            higher_row = ((int) line[1].charAt(0)) - 65;
            higher_col = ((int) line[1].charAt(1)) - 49;

            Variable<Integer> lower_variable = variableList.get(lower_row * dimensions + lower_col);
            Variable<Integer> higher_variable = variableList.get(higher_row * dimensions + higher_col);

            lower_variable.appendConstraint(new LowerValue<>(lower_variable, higher_variable, defaultValue));
            higher_variable.appendConstraint(new HigherValue<>(higher_variable, lower_variable, defaultValue));
        }
    }

    @Override
    void parseModel(List<String> data) {

        domain = new ArrayList<>(IntStream.rangeClosed(1, dimensions).boxed().collect(Collectors.toList()));

        //loading variables
        List<String> variablesData = data.subList(1, dimensions + 1);
        loadVariables(variablesData);

        //loading futoshiki constraints
        List<String> constraintData = data.subList(dimensions + 2, data.size());
        loadFutoshikiConstraints(constraintData);

        //loading unique constraints
        setUniqueConstraints();
    }

    @Override
    public String getBoard() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Variable<Integer>> row;
        for(int i = 0; i < dimensions; i++) {
            int row_start = i * dimensions;
            row = variableList.subList(row_start, row_start + dimensions);

            stringBuilder.append(row.stream().map(v -> v.getValue().toString()).collect(Collectors.joining("\t")));
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
