package pp.pwr.constraints;

import pp.pwr.variables.Variable;

import java.util.List;

public class SkyscraperConstraint<T extends  Comparable<T>> implements ConstraintInterface {

    private List<Variable<T>> row;
    T defaultValue;

    private Integer leftConstraint, rightContraint;

    public SkyscraperConstraint(List<Variable<T>> row, Integer leftConstraint, Integer rightConstraint, T defaultValue) {
        this.row = row;
        this.leftConstraint = leftConstraint;
        this.rightContraint = rightConstraint;
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean check() {
        if (leftConstraint == 0 && rightContraint == 0) {
            return true;
        }

        T highest_left = defaultValue;
        T highest_right = defaultValue;
        T value_left, value_right;

        int left_count = 0;
        int right_count = 0;

        int row_size = row.size();

        for(int i = 0; i < row_size; i++) {
            value_left = row.get(i).getValue();
            value_right = row.get(row_size - i - 1).getValue();

            if (value_left.equals(defaultValue) || value_right.equals(defaultValue)) {
                return true;
            }

            if (highest_left.compareTo(value_left) < 0) {
                highest_left = value_left;
                left_count++;
            }

            if (highest_right.compareTo(value_right) < 0) {
                highest_right = value_right;
                right_count++;
            }

        }

        boolean left_check = (leftConstraint == 0 || left_count == leftConstraint);
        boolean right_check = (rightContraint == 0 || right_count == rightContraint);

        return left_check && right_check;
    }

    @Override
    public List<Variable> getConstrained() {
        return null;
    }
}
