package de.hanbei.resources.http;

import de.hanbei.resources.ResourceLocator;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

/** @author fschulz */
public class HttpResourceLocator implements ResourceLocator {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpResourceLocator.class);

    private URI resourceBase;

    public HttpResourceLocator() throws URISyntaxException {
        resourceBase = null;
    }


    public HttpResourceLocator(String base) throws URISyntaxException {
        this(new URI(base));
    }

    public HttpResourceLocator(URI base) {
        this.resourceBase = base;
        LOGGER.info("Using {} as resource base.", resourceBase.toString());
    }

    public InputStream getResource(String name) {
        HttpClient client = new DefaultHttpClient();
        try {
            URI resourceUri = new URI(name);
            HttpGet get = new HttpGet(resourceBase.resolve(resourceUri));
            HttpResponse response = client.execute(get);
            if (200 == response.getStatusLine().getStatusCode()) {
                return response.getEntity().getContent();
            } else {
                LOGGER.warn("Resource {} not found on base {}", name, resourceBase);
                return null;
            }
        } catch (URISyntaxException e) {
            // exceptions are allowed as null will be returned.
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        } finally {
            client.getConnectionManager().shutdown();
        }
        LOGGER.warn("Resource {} not found on base {}", name, resourceBase);
        return null;
    }

}
