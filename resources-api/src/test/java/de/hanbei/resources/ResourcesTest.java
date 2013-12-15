/* Copyright 2011 Florian Schulz

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. */
package de.hanbei.resources;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @author fschulz
 */
public class ResourcesTest {

    private Resources resources;
    private MockResourceLocator resourceLocator;

    @Before
    public void setUp() {
        resources = new Resources();
        resourceLocator = new MockResourceLocator();
        resources.addResourceLocator(resourceLocator);
    }

    @Test
    public void testGetResourceAsStream() throws Exception {
        InputStream stream = resources.getResourceAsStream("test");
        assertNotNull(stream);
        byte[] buffer = new byte[32];
        int bytesRead = stream.read(buffer);
        String s = new String(Arrays.copyOfRange(buffer, 0, bytesRead));
        assertEquals(MockResourceLocator.TEST_RESOURCE, s);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetNotPresentResourceAsStream() throws Exception {
        resources.getResourceAsStream("test2");
    }

    @Test
    public void testGetResourceAsReader() throws Exception {
        Reader stream = resources.getResourceAsReader("test");
        assertNotNull(stream);
        char[] buffer = new char[32];
        int bytesRead = stream.read(buffer);
        String s = new String(Arrays.copyOfRange(buffer, 0, bytesRead));
        assertEquals(MockResourceLocator.TEST_RESOURCE, s);
    }

    @Test
    public void testGetResourceAsReaderWithEncoding() throws Exception {
        Reader stream = resources.getResourceAsReader("test", Charset.forName("ISO8859_1"));
        assertNotNull(stream);
        char[] buffer = new char[32];
        int bytesRead = stream.read(buffer);
        String s = new String(Arrays.copyOfRange(buffer, 0, bytesRead));
        assertEquals(MockResourceLocator.TEST_RESOURCE, s);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetResourceAsReaderWithNotSupportedEncoding()
            throws Exception {
        resources.getResourceAsReader("test2", Charset.forName("ISO8859_1"));
    }

    @Test
    public void testAddRemoveResourceLocator() throws Exception {
        resources.addResourceLocator(new ResourceLocator() {
            public InputStream getResource(String name) {
                return null;
            }
        });
        Reader stream = resources.getResourceAsReader("test");
        assertNotNull(stream);

        boolean exceptionThrown = false;
        resources.removeResourceLocator(resourceLocator);
        try {
            resources.getResourceAsReader("test");
        } catch (ResourceNotFoundException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
        resources.addResourceLocator(resourceLocator);
        Reader stream3 = resources.getResourceAsReader("test");
        assertNotNull(stream3);
    }

}
