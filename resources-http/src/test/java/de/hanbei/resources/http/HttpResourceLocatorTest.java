package de.hanbei.resources.http;

import de.hanbei.httpserver.MockHttpServer;
import de.hanbei.httpserver.common.Method;
import de.hanbei.httpserver.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @author fschulz
 */
public class HttpResourceLocatorTest {
    private HttpResourceLocator resourceLocator;
    private MockHttpServer server;

    @Before
    public void setUp() throws Exception {
        server = new MockHttpServer();
        server.setPort(7001);
        server.addResponse(Method.GET, new URI("/test.html"), Response.ok().content("TestResource").build());
        server.start();
        resourceLocator = new HttpResourceLocator("http://localhost:7001/");
    }

    @Test
    public void testGetResourceAsStream() throws Exception {
        InputStream stream = resourceLocator.getResource("test.html");
        assertNotNull(stream);
        byte[] buffer = new byte[32];
        int bytesRead = stream.read(buffer);
        String s = new String(Arrays.copyOfRange(buffer, 0, bytesRead));
        assertEquals("TestResource", s);
    }

    @Test
    public void testGetNotExistingResourceAsStream() throws Exception {
        assertNull(resourceLocator.getResource("doesnotexist.txt"));
    }

    @After
    public void tearDown() {
        server.stop();
    }
}
