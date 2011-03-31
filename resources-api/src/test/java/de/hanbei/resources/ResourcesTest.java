package de.hanbei.resources;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.io.Reader;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/** @author fschulz */
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
		Reader stream = resources.getResourceAsReader("test", "ISO8859_1");
		assertNotNull(stream);
		char[] buffer = new char[32];
		int bytesRead = stream.read(buffer);
		String s = new String(Arrays.copyOfRange(buffer, 0, bytesRead));
		assertEquals(MockResourceLocator.TEST_RESOURCE, s);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testGetResourceAsReaderWithNotSupportedEncoding()
			throws Exception {
		resources.getResourceAsReader("test", "ISO85167");
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
