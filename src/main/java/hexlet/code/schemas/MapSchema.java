package hexlet.code.schemas;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public final class MapSchema<K, V> extends BaseSchema<Map<K, V>> {
    private final Function<Map<String, BaseSchema<V>>, Predicate<Map<K, V>>> shapeValidator = schemas -> map -> schemas
            .entrySet()
            .stream()
            .allMatch(sc -> {
                final String key = sc.getKey();
                final BaseSchema<V> validator = sc.getValue();
                return validator.isValid(map.get(key));
            });
    private final Function<Integer, Predicate<Map<K, V>>> sizeValidator = (size) -> map -> Optional
            .ofNullable(map)
            .map(value -> value.size() == size)
            .orElse(false);
    private final Predicate<Map<K, V>> requiredValidator = map -> Optional
            .ofNullable(map)
            .isPresent();

    public MapSchema<K, V> required() {
        addValidator(requiredValidator);
        return this;
    }

    public MapSchema<K, V> sizeof(int size) {
        addValidator(sizeValidator.apply(size));
        return this;
    }

    public MapSchema<K, V> shape(Map<String, BaseSchema<V>> schemas) {
        addValidator(shapeValidator.apply(schemas));
        return this;
    }
}
