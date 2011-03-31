package de.hanbei.resources.classpath;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Arrays;

import static org.junit.Assert.*;

/** @author fschulz */
public class ClasspathResourceLocatorTest {
    private ClasspathResourceLocator resourceLocator;

    @Before
    public void setUp() throws Exception {
        resourceLocator = new ClasspathResourceLocator();
    }

    @Test
    public void testGetResource() throws Exception {
        InputStream stream = resourceLocator.getResource("test.txt");
        assertNotNull(stream);
        byte[] buffer = new byte[32];
        int bytesRead = stream.read(buffer);
        String s = new String(Arrays.copyOfRange(buffer, 0, bytesRead));
        assertEquals("TestResource", s);
    }

    @Test
    public void testGetNotExistingResource() throws Exception {
        assertNull(resourceLocator.getResource("doesnotexist.txt"));
    }
}
