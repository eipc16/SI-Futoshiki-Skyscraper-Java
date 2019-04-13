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

    public SkyscraperModel(String filePath, String fileName) {
        super(filePath, fileName,0);

        constraintValues = new HashMap<>();

        loadModel();
    }

    private void initVariables() {
        for(int i = 0; i < dimensions; i++ ) {
            for(int j = 0; j < dimensions; j++) {
                Variable<Integer> variable = new PuzzleVariable(defaultValue, j, i, false);
                variable.setDomain(new ArrayList<>(IntStream.rangeClosed(1, dimensions).boxed().collect(Collectors.toList())));
                variableList.add(variable);
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

        for(int i = 0; i < variableList.size(); i++) {
            column = new ArrayList<>();
            for(int j = i % dimensions; j < variableList.size(); j += dimensions) {
                column.add(variableList.get(j));

                if(i == 0) {
                    if (constraintValues.get("L").get(j / dimensions) == 1) {
                        variableList.get(j).update(variableList.get(j).getMaxPossible());
                        variableList.get(j).setPredefined();
                    }
                } else if (i == variableList.size() - 1) {
                    if (constraintValues.get("P").get(j / dimensions) == 1) {
                        variableList.get(j).update(variableList.get(j).getMaxPossible());
                        variableList.get(j).setPredefined();
                    }
                }
            }

            variableList.get(i).appendConstraint(new SkyscraperConstraint<>(column, constraintValues.get("G").get(i % dimensions), constraintValues.get("D").get(i % dimensions), defaultValue));
            variableList.get(i).appendConstrainedVariables(column);

            row_start = (i / dimensions) * dimensions;

            row = new ArrayList<>();
            for(int j = row_start; j < row_start + dimensions; j++) {
                row.add(variableList.get(j));

                if(i == 0) {
                    if (constraintValues.get("G").get(j % dimensions) == 1) {
                        variableList.get(j).update(variableList.get(j).getMaxPossible());
                        variableList.get(j).setPredefined();
                    }
                } else if (i == variableList.size() - 1) {
                    if (constraintValues.get("D").get(j % dimensions) == 1) {
                        variableList.get(j).update(variableList.get(j).getMaxPossible());
                        variableList.get(j).setPredefined();
                    }
                }
            }
            variableList.get(i).appendConstraint(new SkyscraperConstraint<>(row, constraintValues.get("L").get(i / dimensions), constraintValues.get("P").get(i / dimensions), defaultValue));
            variableList.get(i).appendConstrainedVariables(row);
        }

        for(int i = 0; i < variableList.size(); i++) {
            if(variableList.get(i).isPredefined()) {
                System.out.println(variableList.get(i).getName());
            }
        }
    }

    @Override
    void parseModel(List<String> data) {
        domain = IntStream.rangeClosed(1, dimensions).boxed().collect(Collectors.toList());

        //set up variables to defaultValue
        initVariables();

        //set up skyscraper constraints
        setUpSkyscraperConstraints(data);

        //set up unique constraints
        setUniqueConstraints();
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

    @Override
    public void dumpHTML(String methodName) {

    }
}
