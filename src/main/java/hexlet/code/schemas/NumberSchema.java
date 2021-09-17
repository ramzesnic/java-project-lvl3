package hexlet.code.schemas;

import java.time.temporal.ValueRange;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public final class NumberSchema extends BaseSchema<Integer> {
    private static final Map<String, BiFunction<Integer, Integer, Predicate<Integer>>> VALIDATORS = Map.of(
            REQUIRED, (min, max) -> (data) -> Optional.ofNullable(data)
                    .isPresent(),
            POSITIVE, (min, max) -> (data) -> Optional.ofNullable(data)
                    .map(value -> value >= 0)
                    .orElse(true),
            RANGE, (min, max) -> (data) -> Optional.ofNullable(data)
                    .map(value -> ValueRange.of(min, max).isValidIntValue(data))
                    .orElse(false));

    private void selectValidator(String validatorName, int min, int max) {
        final Predicate<Integer> validator = VALIDATORS
                .get(validatorName)
                .apply(min, max);
        getSelectedValidators().add(validator);
    }

    public NumberSchema required() {
        this.selectValidator(REQUIRED, 0, 0);
        return this;
    }

    public NumberSchema positive() {
        this.selectValidator(POSITIVE, 0, 0);
        return this;
    }

    public NumberSchema range(int min, int max) {
        this.selectValidator(RANGE, min, max);
        return this;
    }
}
