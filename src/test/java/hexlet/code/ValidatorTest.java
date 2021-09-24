package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import hexlet.code.schemas.StringSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.BaseSchema;

class ValidatorTest {
    private static final String TEST1_STR = "what does the fox say";
    private static final String TEST2_STR = "";
    private static final String TEST3_STR = "asdf";
    private static final String CONSTAINTS1_STR = "what";
    private static final String CONSTAINTS2_STR = "whatttt";

    @Test
    void testStringValidator() {
        final int minLength = 5;
        final Validator v = new Validator();
        final StringSchema schema = v.string();
        assertTrue(schema.isValid(TEST2_STR));
        assertTrue(schema.isValid(null));

        schema.required();

        assertTrue(schema.isValid(TEST1_STR));
        assertTrue(schema.isValid(TEST1_STR));
        assertFalse(schema.isValid(TEST2_STR));
        assertFalse(schema.isValid(null));

        schema.minLength(minLength);

        assertFalse(schema.isValid(TEST3_STR));
        assertTrue(schema.isValid(TEST1_STR));

        schema.contains(CONSTAINTS1_STR);

        assertTrue(schema.isValid(TEST1_STR));

        schema.contains(CONSTAINTS2_STR);

        assertFalse(schema.isValid(TEST1_STR));
    }

    @Test
    void testNumberValidator() {
        final Validator v = new Validator();
        final NumberSchema schema = v.number();
        final int ten = 10;
        final int tenNegative = -10;
        final int five = 5;
        final int four = 4;
        final int eleven = 11;

        assertTrue(schema.isValid(null));

        schema.required();

        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(ten));
        assertFalse(schema.isValid("5"));
        assertTrue(schema.positive().isValid(ten));
        assertFalse(schema.isValid(0));
        assertFalse(schema.isValid(tenNegative));

        schema.range(five, ten);

        assertTrue(schema.isValid(five));
        assertTrue(schema.isValid(ten));
        assertFalse(schema.isValid(four));
        assertFalse(schema.isValid(eleven));
    }

    @Test
    void testMapValidator() {
        final Validator v = new Validator();
        final MapSchema schema = v.map();

        assertTrue(schema.isValid(null));

        schema.required();

        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(new HashMap<>()));

        final Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertTrue(schema.isValid(data));

        schema.sizeof(2);

        schema.isValid(data);
        data.put("key2", "value2");
        schema.isValid(data);
    }

    @Test
    void testMapValueValidator() {
        final Validator v = new Validator();
        final MapSchema schema = v.map();
        final int hundred = 100;
        final int fiveNegative = -5;

        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("name", v.string().required());
        schemas.put("age", v.number().positive());
        schema.shape(schemas);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "Kolya");
        human1.put("age", hundred);
        assertTrue(schema.isValid(human1));

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "Maya");
        human2.put("age", null); // true
        assertTrue(schema.isValid(human2));

        Map<String, Object> human3 = new HashMap<>();
        human3.put("name", "");
        human3.put("age", null);
        assertFalse(schema.isValid(human3));

        Map<String, Object> human4 = new HashMap<>();
        human4.put("name", "Valya");
        human4.put("age", fiveNegative);
        assertFalse(schema.isValid(human4));
    }
}
