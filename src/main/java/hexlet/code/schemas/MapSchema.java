package hexlet.code.schemas;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public final class MapSchema<K, V> extends BaseSchema<Map<K, V>> {
    private Predicate<Map<K, V>> shapeValidator(Integer size, Map<String, BaseSchema> schemas) {
        return map -> schemas.entrySet().stream()
                .map(sc -> {
                    final String key = sc.getKey();
                    final BaseSchema validator = sc.getValue();
                    return validator.isValid(map.get(key));
                }).allMatch(value -> !!value);
    }

    private Map<String, BiFunction<Integer, Map<String, BaseSchema>, Predicate<Map<K, V>>>> validators = Map
            .of(
                    REQUIRED, (size, schemas) -> map -> Optional.ofNullable(map)
                            .isPresent(),
                    SIZE, (size, schemas) -> map -> Optional.ofNullable(map)
                            .map(value -> value.size() == size)
                            .orElse(false),
                    MAP_SHAPE, this::shapeValidator);

    private void selectValidator(String validatorName, int size, Map<String, BaseSchema> schemas) {
        final Predicate<Map<K, V>> validator = validators
                .get(validatorName)
                .apply(size, schemas);
        getSelectedValidators().add(validator);
    }

    public MapSchema<K, V> required() {
        this.selectValidator(REQUIRED, 0, null);
        return this;
    }

    public MapSchema<K, V> sizeof(int size) {
        this.selectValidator(SIZE, size, null);
        return this;
    }

    public MapSchema<K, V> shape(Map<String, BaseSchema> schemas) {
        this.selectValidator(MAP_SHAPE, 0, schemas);
        return this;
    }
}
