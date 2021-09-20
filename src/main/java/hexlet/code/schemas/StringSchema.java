package hexlet.code.schemas;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public final class StringSchema extends BaseSchema<String> {
    private static final Map<String, BiFunction<String, Integer, Predicate<String>>> VALIDATORS = Map.of(
            REQUIRED, (search, minLength) -> (data) -> !Optional.ofNullable(data)
                    .orElse(BLANK)
                    .isBlank(),
            CONSTAINTS, (search, minLength) -> (data) -> Optional.ofNullable(data)
                    .orElse(BLANK)
                    .contains(search),
            MIN_LENGHT, (search, minLength) -> (data) -> Optional.ofNullable(data)
                    .map(str -> str.length() >= minLength)
                    .orElse(false));

    private void selectValidator(String validatorName, String search, int minLength) {
        final Predicate<String> validator = VALIDATORS
                .get(validatorName)
                .apply(search, minLength);
        getSelectedValidators().add(validator);
    }

    public StringSchema required() {
        this.selectValidator(REQUIRED, BLANK, 0);
        return this;
    }

    public StringSchema minLength(int minLength) {
        this.selectValidator(MIN_LENGHT, BLANK, minLength);
        return this;
    }

    public StringSchema containts(String search) {
        this.selectValidator(CONSTAINTS, search, 0);
        return this;
    }
}
