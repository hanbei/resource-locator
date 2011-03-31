package de.hanbei.resources.fs;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/** @author fschulz */
public class ZipResourceLocatorTest {
	private ZipResourceLocator zipResourceLocator;

	@Before
	public void setUp() throws Exception {
		URL resource = getClass().getResource("/test.zip");
		zipResourceLocator = new ZipResourceLocator(new File(resource.toURI()));
	}

	@Test
	public void testGetResource() throws Exception {
		InputStream stream = zipResourceLocator.getResource("test.txt");
		assertNotNull(stream);
		byte[] buffer = new byte[32];
		int bytesRead = stream.read(buffer);
		String s = new String(Arrays.copyOfRange(buffer, 0, bytesRead));
		assertEquals("TestResource", s);
	}

	@Test
	public void testGetNotExistingResource() throws Exception {
		assertNull(zipResourceLocator.getResource("doesnotexist.txt"));
	}
}
