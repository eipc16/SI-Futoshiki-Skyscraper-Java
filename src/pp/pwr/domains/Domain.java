package pp.pwr.domains;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Domain {
    private Set<Object> domain;

    public Domain(List<Object> domain) {
        this.domain = new HashSet<>();
        this.domain.addAll(domain);
    }

    public boolean contains(Object value) {
        return this.domain.contains(value);
    }

    public boolean remove(Object value) {
        return this.domain.remove(value);
    }

    public boolean add(Object value) {
        return this.domain.add(value);
    }

    public Set<Object> values() {
        return this.domain;
    }

    @Override
    public String toString() {
        return this.domain.stream().map(Object::toString).collect(Collectors.joining(", "));
    }
}
