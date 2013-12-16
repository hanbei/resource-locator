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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hanbei.resources.ResourceLocator;

/**
 * @author fschulz
 */
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
                HttpEntity entity = response.getEntity();
                return new ByteArrayInputStream(EntityUtils.toByteArray(entity));
            } else {
                LOGGER.warn("Resource {} not found on base {}", name, resourceBase);
                return null;
            }
        }
        catch(URISyntaxException e) {
            // exceptions are allowed as null will be returned.
        }
        catch(ClientProtocolException e) {
        }
        catch(IOException e) {
        } finally {
            client.getConnectionManager().shutdown();
        }
        LOGGER.warn("Resource {} not found on base {}", name, resourceBase);
        return null;
    }

}
