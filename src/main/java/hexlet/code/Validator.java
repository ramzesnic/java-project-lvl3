package hexlet.code;

public class Validator {
    public final StringSchema string() {
        return new StringSchema();
    }

    public final NumberSchema number() {
        return new NumberSchema();
    }
}
