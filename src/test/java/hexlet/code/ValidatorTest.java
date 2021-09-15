package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ValidatorTest {
    private static final String TEST1_STR = "what does the fox say";
    private static final String TEST2_STR = "";
    private static final String CONSTAINTS1_STR = "what";
    private static final String CONSTAINTS2_STR = "whatttt";

    @Test
    void testStringValidator() {
        final Validator v = new Validator();
        final StringSchema schema = v.string();
        assertEquals(schema.isValid(TEST2_STR), true);
        assertEquals(schema.isValid(null), true);

        schema.requared();

        assertEquals(schema.isValid(TEST1_STR), true);
        assertEquals(schema.isValid(TEST1_STR), true);
        assertEquals(schema.isValid(TEST2_STR), false);
        assertEquals(schema.isValid(null), false);

        schema.containts(CONSTAINTS1_STR);

        assertEquals(schema.isValid(TEST1_STR), true);

        schema.containts(CONSTAINTS2_STR);

        assertEquals(schema.isValid(TEST1_STR), false);
    }
}
