package hexlet.code;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

class StringSchema extends BaseSchema<String> {
    private static final Map<String, Function<String, Predicate<String>>> VALIDATORS = Map.of(
            REQUARED, (search) -> (data) -> !Optional.ofNullable(data)
                    .orElse(BLANK)
                    .isBlank(),
            CONSTAINTS, (search) -> (data) -> Optional.ofNullable(data)
                    .orElse(BLANK)
                    .contains(search));

    private void selectValidator(String validatorName, String search) {
        final Predicate<String> validator = VALIDATORS
                .get(validatorName)
                .apply(search);
        selectedValidators.add(validator);
    }

    public StringSchema requared() {
        this.selectValidator(REQUARED, BLANK);
        return this;
    }

    public StringSchema containts(String search) {
        this.selectValidator(CONSTAINTS, search);
        return this;
    }
}
