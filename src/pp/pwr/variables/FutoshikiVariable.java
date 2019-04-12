package pp.pwr.variables;

import pp.pwr.domains.Domain;

public class FutoshikiVariable extends Variable<Integer> implements Comparable<FutoshikiVariable> {

    private int row_position, column_position;

    private static final String[] column_labels = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
    private static final String[] row_labels = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

    public FutoshikiVariable(Integer value, int column_position, int row_position, Domain domain, boolean predefined) {
        super(value, domain, predefined);
        this.row_position = row_position;
        this.column_position = column_position;
    }

    public FutoshikiVariable(Integer value, int column_position, int row_position, Domain domain) {
        this(value, row_position, column_position, domain, false);
    }

    @Override
    public String getName() {
        return String.format("%s%s", column_labels[column_position], row_labels[row_position]);
    }

    @Override
    public int compareTo(FutoshikiVariable futoshikiVariable) {
        return this.value.compareTo(futoshikiVariable.value);
    }
}
