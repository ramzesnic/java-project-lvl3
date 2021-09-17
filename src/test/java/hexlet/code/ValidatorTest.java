package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    private static final String CONSTAINTS1_STR = "what";
    private static final String CONSTAINTS2_STR = "whatttt";

    @Test
    void testStringValidator() {
        final Validator v = new Validator();
        final StringSchema schema = v.string();
        assertEquals(schema.isValid(TEST2_STR), true);
        assertEquals(schema.isValid(null), true);

        schema.required();

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
        final int ten = 10;
        final int tenNegative = -10;
        final int five = 5;
        final int four = 4;
        final int eleven = 11;

        assertEquals(schema.isValid(null), true);

        schema.required();

        assertEquals(schema.isValid(null), false);
        assertEquals(schema.isValid(ten), true);
        assertEquals(schema.positive().isValid(ten), true);
        assertEquals(schema.isValid(tenNegative), false);

        schema.range(five, ten);

        assertEquals(schema.isValid(five), true);
        assertEquals(schema.isValid(ten), true);
        assertEquals(schema.isValid(four), false);
        assertEquals(schema.isValid(eleven), false);
    }

    @Test
    void testMapValidator() {
        final Validator v = new Validator();
        final MapSchema schema = v.map();

        assertEquals(schema.isValid(null), true);

        schema.required();

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
        assertEquals(schema.isValid(human1), true);

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "Maya");
        human2.put("age", null); // true
        assertEquals(schema.isValid(human2), true);

        Map<String, Object> human3 = new HashMap<>();
        human3.put("name", "");
        human3.put("age", null);
        assertEquals(schema.isValid(human3), false);

        Map<String, Object> human4 = new HashMap<>();
        human4.put("name", "Valya");
        human4.put("age", fiveNegative);
        assertEquals(schema.isValid(human4), false);
    }
}
