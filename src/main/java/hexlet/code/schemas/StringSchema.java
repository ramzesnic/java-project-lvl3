package hexlet.code.schemas;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Function;

public final class StringSchema extends BaseSchema<String> {
    protected static final String BLANK = "";
    private final Predicate<String> requiredValidator = (data) -> !Optional.ofNullable(data)
            .orElse(BLANK)
            .isBlank();
    private final Function<String, Predicate<String>> containsValidator = (search) -> (data) -> Optional
            .ofNullable(data)
            .orElse(BLANK)
            .contains(search);
    private final Function<Integer, Predicate<String>> minLengthValidator = (minLength) -> (data) -> Optional
            .ofNullable(data)
            .map(str -> str.length() >= minLength)
            .orElse(false);

    public StringSchema required() {
        addValidator(requiredValidator);
        return this;
    }

    public StringSchema minLength(int minLength) {
        addValidator(minLengthValidator.apply(minLength));
        return this;
    }

    public StringSchema contains(String search) {
        addValidator(containsValidator.apply(search));
        return this;
    }
}
