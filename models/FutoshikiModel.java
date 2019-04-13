package pp.pwr.models;

import pp.pwr.constraints.HigherValue;
import pp.pwr.constraints.LowerValue;
import pp.pwr.constraints.Unique;
import pp.pwr.variables.PuzzleVariable;
import pp.pwr.variables.Variable;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FutoshikiModel extends Model<Integer> {

    public FutoshikiModel(String filePath, String fileName) {
        super(filePath, fileName,0);

        loadModel();
    }

    void loadVariables(List<String> data) {
        Integer value;
        boolean predefined;

        this.domain = new ArrayList<>(IntStream.rangeClosed(1, dimensions).boxed().collect(Collectors.toList()));


        for(int i = 0; i < data.size(); i++ ) {
            String[] line = data.get(i).split(";");

            for(int j = 0; j < line.length; j++) {
                value = Integer.parseInt(line[j]);
                predefined = !value.equals(defaultValue);
                Variable<Integer> variable = new PuzzleVariable(value, j, i, predefined);

                if(!predefined) {
                    variable.setDomain(new ArrayList<>(IntStream.rangeClosed(1, dimensions).boxed().collect(Collectors.toList())));
                }

                variableList.add(variable);
            }
        }
    }

    void loadFutoshikiConstraints(List<String> data) {
        int lower_row, lower_col, higher_row, higher_col;

        for(int i = 0; i < data.size(); i++) {
            if(data.get(i).length() > 0) {
                String[] line = data.get(i).split(";");

                lower_row = ((int) line[0].charAt(0)) - 65;
                lower_col = ((int) line[0].charAt(1)) - 49;

                higher_row = ((int) line[1].charAt(0)) - 65;
                higher_col = ((int) line[1].charAt(1)) - 49;

                Variable<Integer> lower_variable = variableList.get(lower_row * dimensions + lower_col);
                Variable<Integer> higher_variable = variableList.get(higher_row * dimensions + higher_col);

                higher_variable.removePossibleValue(domain.get(0));
                lower_variable.removePossibleValue(domain.get(domain.size() - 1));

                lower_variable.appendConstrainedVariable(higher_variable);
                higher_variable.appendConstrainedVariable(lower_variable);

                lower_variable.appendConstraint(new LowerValue<>(lower_variable, higher_variable, defaultValue));
                higher_variable.appendConstraint(new HigherValue<>(higher_variable, lower_variable, defaultValue));
            }
        }
    }

    @Override
    void parseModel(List<String> data) {

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

    @Override
    public void dumpHTML(String methodName) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Variable<Integer>> row = variableList.subList(0, dimensions);
        stringBuilder.append(String.format("<table class=\"%s\">\n", methodName));
        stringBuilder.append(String.format("<caption>File: %s</caption>", fileName));
        stringBuilder.append("\t<tr>\n");
        stringBuilder.append("\t\t<td></td>\n");
        row.stream()
                .map(v -> String.format("\t\t<td class=\"columnLabel\">%s</td>\n", v.getName().charAt(1)))
                .forEach(stringBuilder::append);
        stringBuilder.append("\t</tr>\n");
        for(int i = 0; i < dimensions; i++) {
            int row_start = i * dimensions;
            row = variableList.subList(row_start, row_start + dimensions);

            stringBuilder.append("\t<tr class=\"row\">\n");
            stringBuilder.append(String.format("\t\t<td class=\"rowLabel\">%s</td>\n", row.get(0).getName().charAt(0)));

            row.stream()
                    .map(v -> String.format("\t\t<td class=\"cellValue\">%s</td>\n", v.getValue().toString()))
                    .forEach(stringBuilder::append);

            //stringBuilder.append(row.stream().map(v -> v.getValue().toString()).collect(Collectors.joining("\n")));
            stringBuilder.append("\t</tr>\n");
        }
        stringBuilder.append("</table>");

        Path path = Paths.get(String.format("pp/pwr/Data/HTML/%s.html", fileName));

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)){
            bufferedWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
