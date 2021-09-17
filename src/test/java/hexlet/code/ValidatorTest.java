package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

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

    @Test
    void testNumberValidator() {
        final Validator v = new Validator();
        final NumberSchema schema = v.number();

        assertEquals(schema.isValid(null), true);

        schema.required();

        assertEquals(schema.isValid(null), false);
        assertEquals(schema.isValid(10), true);
        //assertEquals(schema.isValid("5"), true);
        assertEquals(schema.positive().isValid(10), true);
        assertEquals(schema.isValid(-10), false);

        schema.range(5, 10);

        assertEquals(schema.isValid(5), true);
        assertEquals(schema.isValid(10), true);
        assertEquals(schema.isValid(4), false);
        assertEquals(schema.isValid(11), false);
    }

    @Test
    void testMapValidator() {
        final Validator v = new Validator();
        final MapSchema schema = v.map();

        assertEquals(schema.isValid(null), true);

        schema.requared();

        assertEquals(schema.isValid(null), false);
        assertEquals(schema.isValid(new HashMap<>()), true);

        final Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertEquals(schema.isValid(data), true);

        schema.sizeof(2);

        schema.isValid(data);
        data.put("key2", "value2");
        schema.isValid(data);
    }
}
