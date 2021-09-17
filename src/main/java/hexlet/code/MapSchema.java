package hexlet.code;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

class MapSchema<K, V> extends BaseSchema<Map<K, V>> {
    private final Map<String, Function<Integer, Predicate<Map<K, V>>>> validators = Map.of(
            REQUARED, size -> map -> Optional.ofNullable(map)
                    .isPresent(),
            SIZE, size -> map -> Optional.ofNullable(map)
                    .map(value -> value.size() == size)
                    .orElse(false));

    private void selectValidator(String validatorName, int size) {
        final Predicate<Map<K, V>> validator = validators
                .get(validatorName)
                .apply(size);
        selectedValidators.add(validator);
    }

    public MapSchema<K, V> requared() {
        this.selectValidator(REQUARED, 0);
        return this;
    }

    public MapSchema<K, V> sizeof(int size) {
        this.selectValidator(SIZE, size);
        return this;
    }
}
