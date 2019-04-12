package pp.pwr.models;

import pp.pwr.constraints.ConstraintInterface;
import pp.pwr.domains.Domain;
import pp.pwr.variables.Variable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Model<T extends Comparable<T>> {

    List<Variable<T>> variableList;
    Domain domain;
    List<ConstraintInterface<T>> constraintList;
    T defaultValue;

    int dimensions;

    public Model(T defaultValue) {
        this.defaultValue = defaultValue;
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

    abstract void parseModel(List<String> data);

    abstract boolean validate();

    abstract String getBoard();

}
