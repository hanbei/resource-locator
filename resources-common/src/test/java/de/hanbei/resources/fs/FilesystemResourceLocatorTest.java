package de.hanbei.resources.fs;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;

import static org.junit.Assert.*;

/** @author fschulz */
public class FilesystemResourceLocatorTest {
	private FilesystemResourceLocator filesystemResourceLocator;

	@Before
	public void setUp() throws Exception {
		URL resource = getClass().getResource("/test.txt");
		filesystemResourceLocator = new FilesystemResourceLocator(new File(
				resource.toURI()).getParentFile().getAbsolutePath());
	}

	@Test
	public void testGetResource() throws Exception {
		InputStream stream = filesystemResourceLocator.getResource("test.txt");
		assertNotNull(stream);
		byte[] buffer = new byte[32];
		int bytesRead = stream.read(buffer);
		String s = new String(Arrays.copyOfRange(buffer, 0, bytesRead));
		assertEquals("TestResource", s);
	}

	@Test
	public void testGetNotExistingResource() throws Exception {
		assertNull(filesystemResourceLocator.getResource("doesnotexist.txt"));
	}
}
