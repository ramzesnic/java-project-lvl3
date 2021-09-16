package hexlet.code;

import java.time.temporal.ValueRange;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;

class NumberSchema extends BaseSchema<Integer> {
    private static final Map<String, BiFunction<Integer, Integer, Predicate<Integer>>> VALIDATORS = Map.of(
            REQUARED, (min, max) -> (data) -> Optional.ofNullable(data)
                    .isPresent(),
            POSITIVE, (min, max) -> (data) -> Optional.ofNullable(data)
                    .map(value -> value >= 0)
                    .orElse(false),
            RANGE, (min, max) -> (data) -> Optional.ofNullable(data)
                    .map(value -> ValueRange.of(min, max).isValidIntValue(data))
                    .orElse(false));

    private void selectValidator(String validatorName, int min, int max) {
        final Predicate<Integer> validator = VALIDATORS
                .get(validatorName)
                .apply(min, max);
        selectedValidators.add(validator);
    }

    public NumberSchema required() {
        this.selectValidator(REQUARED, 0, 0);
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
