package org.hubert_lasota.BusinessManagement.input;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class UserInputReaderTest {

    @Test
    void testCheckTypeAndReturnNumber_Integer() throws Exception {
        Method method = UserInputReader.class.getDeclaredMethod("checkTypeAndReturnNumber", Class.class, String.class);
        method.setAccessible(true);

        Integer result = (Integer) method.invoke(null, Integer.class, "42");
        assertEquals(42, result);
    }

    @Test
    void testCheckTypeAndReturnNumber_Long() throws Exception {
        Method method = UserInputReader.class.getDeclaredMethod("checkTypeAndReturnNumber", Class.class, String.class);
        method.setAccessible(true);

        Long result = (Long) method.invoke(null, Long.class, "123456789012345");
        assertEquals(123456789012345L, result);
    }

    @Test
    void testCheckTypeAndReturnNumber_BigDecimal() throws Exception {
        Method method = UserInputReader.class.getDeclaredMethod("checkTypeAndReturnNumber", Class.class, String.class);
        method.setAccessible(true);

        BigDecimal result = (BigDecimal) method.invoke(null, BigDecimal.class, "3.14");
        assertEquals(new BigDecimal("3.14"), result);
    }

    @Test
    void testCheckTypeAndReturnNumber_InvalidInput() throws Exception {
        Method method = UserInputReader.class.getDeclaredMethod("checkTypeAndReturnNumber", Class.class, String.class);
        method.setAccessible(true);

        try {
            method.invoke(null, Integer.class, "invalid");
        } catch (InvocationTargetException e) {
            Throwable actualException = e.getCause();
            assertTrue(actualException instanceof NumberFormatException);
        }
    }

    @Test
    void testCheckTypeAndReturnNumber_UnsupportedType() throws Exception {
        Method method = UserInputReader.class.getDeclaredMethod("checkTypeAndReturnNumber", Class.class, String.class);
        method.setAccessible(true);

        try {
            method.invoke(null, Double.class, "3.14");
        } catch (InvocationTargetException e) {
            Throwable actualException = e.getCause();
            assertTrue(actualException instanceof IllegalArgumentException);
            assertEquals("Unsupported number type", actualException.getMessage());
        }
    }


}
