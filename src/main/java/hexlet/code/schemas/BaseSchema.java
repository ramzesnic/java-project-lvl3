package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    private final List<Predicate<T>> selectedValidators = new ArrayList<>();

    protected final void addValidator(Predicate<T> validator) {
        selectedValidators.add(validator);
    }

    public final boolean isValid(T data) {
        return this.selectedValidators.stream()
                .allMatch(validator -> validator.test(data));
    }
}
