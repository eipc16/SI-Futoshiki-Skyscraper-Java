package pp.pwr.models;

import pp.pwr.constraints.ConstraintInterface;
import pp.pwr.constraints.SkyscraperConstraint;
import pp.pwr.constraints.Unique;
import pp.pwr.variables.PuzzleVariable;
import pp.pwr.variables.Variable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SkyscraperModel extends Model<Integer> {

    Map<String, List<Integer>> constraintValues;
    List<Integer> top, bottom, left, right;

    public SkyscraperModel(String filePath) {
        super(filePath, 0);

        constraintValues = new HashMap<>();

        loadModel();
    }

    private void initVariables() {
        for(int i = 0; i < dimensions; i++ ) {
            for(int j = 0; j < dimensions; j++) {
                variableList.add(new PuzzleVariable(defaultValue, j, i, false));
            }
        }
    }

    private void setUpSkyscraperConstraints(List<String> data) {
        for(String line: data) {
            String[] constraintInfo = line.split(";");
            String[] values = Arrays.copyOfRange(constraintInfo, 1, constraintInfo.length);
            constraintValues.put(constraintInfo[0], Arrays.stream(values).map(Integer::parseInt).collect(Collectors.toList()));
        }

        //columns
        List<Variable<Integer>> column;
        List<Variable<Integer>> row;
        int row_start;

        for(int i = 0; i < dimensions; i++) {

            column = new ArrayList<>();
            for(int j = i % dimensions; j < variableList.size(); j += dimensions) {
                column.add(variableList.get(j));
            }
            constraintList.add(new SkyscraperConstraint<>(column, constraintValues.get("G").get(i), constraintValues.get("D").get(i), defaultValue));

            row_start = i * dimensions;

            row = new ArrayList<>();
            for(int j = row_start; j < row_start + dimensions; j++) {
                row.add(variableList.get(j));
            }
            constraintList.add(new SkyscraperConstraint<>(row, constraintValues.get("L").get(i), constraintValues.get("P").get(i), defaultValue));
        }
    }

    @Override
    public boolean validate() {
        if(super.validate()) {
            for(ConstraintInterface cons: constraintList) {
                if(!cons.check()) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    @Override
    void parseModel(List<String> data) {
        domain = IntStream.rangeClosed(1, dimensions).boxed().collect(Collectors.toList());

        //set up variables to defaultValue
        initVariables();

        //set up unique constraints
        setUniqueConstraints();

        //set up skyscraper constraints
        setUpSkyscraperConstraints(data);
    }

    @Override
    public String getBoard() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Variable<Integer>> row;

        stringBuilder.append("\t");
        stringBuilder.append(constraintValues.get("G").stream().map(v -> String.format("[%d]", v)).collect(Collectors.joining("\t")));
        stringBuilder.append("\n");
        for(int i = 0; i < dimensions; i++) {
            int row_start = i * dimensions;
            row = variableList.subList(row_start, row_start + dimensions);

            stringBuilder.append(String.format("[%d]\t", constraintValues.get("L").get(i)));

            stringBuilder.append(row.stream().map(v -> String.format(" %d ", v.getValue())).collect(Collectors.joining("\t")));

            stringBuilder.append(String.format(" [%d]\t", constraintValues.get("P").get(i)));
            stringBuilder.append("\n");
        }
        stringBuilder.append("\t");
        stringBuilder.append(constraintValues.get("D").stream().map(v -> String.format("[%d]", v)).collect(Collectors.joining("\t")));

        return stringBuilder.toString();
    }
}
