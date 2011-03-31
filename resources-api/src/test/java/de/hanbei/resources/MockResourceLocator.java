package de.hanbei.resources;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/** @author fschulz */
public class MockResourceLocator implements ResourceLocator {

    static final String TEST_RESOURCE = "TestResourceAndSoOn";

    public InputStream getResource(String name) {
        if ("test".equals(name)) {
            ByteArrayInputStream bytesIn = new ByteArrayInputStream(TEST_RESOURCE.getBytes());
            return bytesIn;
        }
        return null;
    }

}
