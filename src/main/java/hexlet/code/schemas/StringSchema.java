package hexlet.code.schemas;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public final class StringSchema extends BaseSchema<String> {
    private static final Map<String, Function<String, Predicate<String>>> VALIDATORS = Map.of(
            REQUIRED, (search) -> (data) -> !Optional.ofNullable(data)
                    .orElse(BLANK)
                    .isBlank(),
            CONSTAINTS, (search) -> (data) -> Optional.ofNullable(data)
                    .orElse(BLANK)
                    .contains(search));

    private void selectValidator(String validatorName, String search) {
        final Predicate<String> validator = VALIDATORS
                .get(validatorName)
                .apply(search);
        getSelectedValidators().add(validator);
    }

    public StringSchema required() {
        this.selectValidator(REQUIRED, BLANK);
        return this;
    }

    public StringSchema containts(String search) {
        this.selectValidator(CONSTAINTS, search);
        return this;
    }
}
