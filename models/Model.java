package pp.pwr.models;

import pp.pwr.constraints.ConstraintInterface;
import pp.pwr.constraints.Unique;
import pp.pwr.variables.Variable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Model<T extends Comparable<T>> {

    List<Variable<T>> variableList;
    List<ConstraintInterface> constraintList;
    List<T> domain;
    T defaultValue;

    String filePath;
    int dimensions;

    public Model(String filePath, T defaultValue) {
        this.filePath = filePath;
        this.defaultValue = defaultValue;
        this.variableList = new ArrayList<>();
        this.constraintList = new ArrayList<>();
    }

    void loadModel() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = Files.newBufferedReader(Paths.get(filePath));

            String dimensions = bufferedReader.readLine();
            this.dimensions = Integer.parseInt(dimensions);

            List<String> data = bufferedReader.lines().collect(Collectors.toList());

            parseModel(data);

        } catch (FileNotFoundException e) {
            System.err.println(String.format("Could not find file: %s", filePath));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void setUniqueConstraints() {
        Variable<T> parentVariable;
        List<Variable<T>> constrainedVariables;
        for(int i = 0; i < variableList.size(); i++) {
            constrainedVariables = new ArrayList<>();
            parentVariable = variableList.get(i);

            int col_start = i % dimensions;
            int row_start = (i / dimensions) * dimensions;

            for(int j = col_start; j < variableList.size(); j += dimensions) {
                if(!variableList.get(j).equals(parentVariable)){
                    constrainedVariables.add(variableList.get(j));
                }
            }

            for(int j = row_start; j < row_start + dimensions; j++) {
                if(!variableList.get(j).equals(parentVariable)){
                    constrainedVariables.add(variableList.get(j));
                }
            }
            parentVariable.appendConstraint(new Unique<>(parentVariable, constrainedVariables, defaultValue));
        }
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public List<Variable<T>> getVariableList() {
        return variableList;
    }

    public List<T> getDomain() {
        return domain;
    }

    public boolean validate() {
        boolean containsFalse = variableList.stream().map(Variable::validate).anyMatch(Boolean.FALSE::equals);
        return !containsFalse;
    }

    abstract void parseModel(List<String> data);

    public abstract String getBoard();
}
