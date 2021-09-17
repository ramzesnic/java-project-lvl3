package hexlet.code;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

abstract class BaseSchema<T> {
    protected static final String REQUARED = "requared";
    protected static final String CONSTAINTS = "constaints";
    protected static final String POSITIVE = "positive";
    protected static final String RANGE = "range";
    protected static final String BLANK = "";
    protected static final String SIZE = "size";

    protected final List<Predicate<T>> selectedValidators = new ArrayList<>();

    public boolean isValid(T data) {
        return this.selectedValidators.stream()
                .map(validator -> validator.test(data))
                .allMatch(res -> !!res);
    }
}
