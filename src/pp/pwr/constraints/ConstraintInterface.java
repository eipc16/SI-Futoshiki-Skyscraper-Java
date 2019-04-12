package pp.pwr.constraints;

import com.sun.org.apache.xpath.internal.operations.Variable;

import java.util.List;

public interface ConstraintInterface {
    boolean check();

    List<Variable> getConstrained();
}
