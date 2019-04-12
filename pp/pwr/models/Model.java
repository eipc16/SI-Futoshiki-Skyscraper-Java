package pp.pwr.models;

import pp.pwr.constraints.ConstraintInterface;
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
    List<ConstraintInterface<T>> constraintList;
    List<T> domain;
    T defaultValue;

    int dimensions;

    public Model(T defaultValue) {
        this.defaultValue = defaultValue;
        this.variableList = new ArrayList<>();
        this.constraintList = new ArrayList<>();
    }

    void loadModel(String filePath) {
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
