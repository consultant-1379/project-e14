import add.Util;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class UtilTest {
    @Test
    public void testIsValidEmail_ValidEmail() {
        assertTrue(Util.isValidEmail("test@example.com"));
    }

    @Test
    public void testIsValidEmail_InvalidEmail() {
        assertFalse(Util.isValidEmail("invalid-email"));
    }

    @Test
    public void testIsInteger_ValidInteger() {
        assertTrue(Util.isInteger("123"));
    }

    @Test
    public void testIsInteger_InvalidInteger() {
        assertFalse(Util.isInteger("abc"));
    }

    @Test
    public void testParseDependencies() {
        String input = "dep1, dep2,  dep3 ,dep4";
        ArrayList<String> result = Util.parseDependencies(input);

        assertEquals(4, result.size());
        assertTrue(result.contains("dep1"));
        assertTrue(result.contains("dep2"));
        assertTrue(result.contains("dep3"));
        assertTrue(result.contains("dep4"));
    }

    @Test
    public void testConvertYAMLToString() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.yaml", "text/plain", "YAML content".getBytes());
        String result = Util.convertYAMLToString(file);

        assertEquals("YAML content", result);
    }
}
