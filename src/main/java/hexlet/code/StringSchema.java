package hexlet.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

class StringSchema {
    private static final String REQUARED = "requared";
    private static final String CONSTAINTS = "constaints";
    private static final String BLANK = "";
    private static final Map<String, Function<String, Predicate<String>>> VALIDATORS = Map.of(
            REQUARED, (search) -> (data) -> !Optional.ofNullable(data)
                    .orElse(BLANK)
                    .isBlank(),
            CONSTAINTS, (search) -> (data) -> Optional.ofNullable(data)
                    .orElse(BLANK)
                    .contains(search));
    private final List<Predicate<String>> selectedValidators = new ArrayList<>();

    public StringSchema requared() {
        final Predicate<String> requared = VALIDATORS
                .get(REQUARED)
                .apply(BLANK);
        selectedValidators.add(requared);
        return this;
    }

    public StringSchema containts(String search) {
        final Predicate<String> containts = VALIDATORS
                .get(CONSTAINTS)
                .apply(search);
        selectedValidators.add(containts);
        return this;
    }

    public boolean isValid(String data) {
        return this.selectedValidators.stream()
                .map(validator -> validator.test(data))
                .allMatch(res -> !!res);
    }
}
