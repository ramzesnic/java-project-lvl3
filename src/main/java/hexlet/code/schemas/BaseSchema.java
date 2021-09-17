package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    protected static final String REQUIRED = "requared";
    protected static final String CONSTAINTS = "constaints";
    protected static final String POSITIVE = "positive";
    protected static final String RANGE = "range";
    protected static final String BLANK = "";
    protected static final String MAP_SHAPE = "shape";
    protected static final String SIZE = "size";

    private final List<Predicate<T>> selectedValidators = new ArrayList<>();

    protected final List<Predicate<T>> getSelectedValidators() {
        return selectedValidators;
    }

    public final boolean isValid(T data) {
        return this.selectedValidators.stream()
                .map(validator -> validator.test(data))
                .allMatch(res -> !!res);
    }
}
