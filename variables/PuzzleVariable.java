package pp.pwr.variables;

import java.util.Set;

public class PuzzleVariable extends Variable<Integer> {

    private int row_position, column_position;

    private static final String[] column_labels = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
    private static final String[] row_labels = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

    public PuzzleVariable(Integer value, int row_position, int column_position, boolean predefined) {
        super(value, predefined);
        this.row_position = row_position;
        this.column_position = column_position;
    }

    public PuzzleVariable(Integer value, int row_position, int column_position, Set<Integer> domain) {
        this(value, row_position, column_position, false);
    }

    @Override
    public String getName() {
        return String.format("%s%s", column_labels[column_position], row_labels[row_position]);
    }
}
