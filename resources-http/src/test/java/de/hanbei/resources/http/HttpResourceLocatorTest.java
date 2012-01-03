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
        server.addResponse(Method.GET, URI.create("/test.html"), Response.ok().content("TestResource").build());
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
