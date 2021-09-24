package hexlet.code.schemas;

import java.time.temporal.ValueRange;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public final class NumberSchema extends BaseSchema<Object> {
    private final Predicate<Object> requiredValidator = (data) -> Optional.ofNullable(data)
            .map(value -> value instanceof Integer)
            .orElse(false);
    private final Predicate<Object> positiveValidator = (data) -> Optional.ofNullable(data)
            .map(value -> value instanceof Integer && (int) value > 0)
            .orElse(true);
    private final BiFunction<Integer, Integer, Predicate<Object>> rangeValidator = (min, max) -> (data) -> Optional
            .ofNullable(data)
            .map(value -> value instanceof Integer && ValueRange.of(min, max).isValidIntValue((int) data))
            .orElse(true);

    public NumberSchema required() {
        addValidator(requiredValidator);
        return this;
    }

    public NumberSchema positive() {
        addValidator(positiveValidator);
        return this;
    }

    public NumberSchema range(int min, int max) {
        addValidator(rangeValidator.apply(min, max));
        return this;
    }
}
