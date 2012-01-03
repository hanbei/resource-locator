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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fschulz
 */
public class Resources {

    private List<ResourceLocator> resourceLocators;

    public Resources() {
        resourceLocators = new ArrayList<ResourceLocator>();
    }

    /**
     * Get a resources by as an inputstream trying all registered resources locators. The first found resources matching
     * the name is returned. If no resources is found a ResourceNotFoundException is thrown.
     *
     * @param name The name of the resources.
     * @return The first found resources with name <code>name</code>.
     * @throws ResourceNotFoundException if no matching resources could be found.
     */
    public InputStream getResourceAsStream(String name) {
        for (ResourceLocator resourceLocator : resourceLocators) {
            InputStream resource = resourceLocator.getResource(name);
            if (resource != null) {
                return resource;
            }
        }
        throw new ResourceNotFoundException("Resource " + name + " not found");
    }

    /**
     * Get a resources as a reader by trying all registered resources locators. The encoding used for the reader will be
     * the platform encoding The first found resources matching the name is returned. If no resources is found a
     * ResourceNotFoundException is thrown.
     *
     * @param name The name of the resources.
     * @return The first found resources with name <code>name</code>.
     * @throws ResourceNotFoundException if no matching resources could be found.
     */
    public Reader getResourceAsReader(String name) {
        return new InputStreamReader(getResourceAsStream(name));
    }

    /**
     * Get a resources as a reader by trying all registered resources locators. The encoding used for the reader will be
     * the specified parameter <code>encoding</code> The first found resources matching the name is returned. If no
     * resources is found a ResourceNotFoundException is thrown.
     *
     * @param name     The name of the resources.
     * @param encoding The encoding to use for the reader.
     * @return The first found resources with name <code>name</code>.
     * @throws ResourceNotFoundException if no matching resources could be found.
     */
    public Reader getResourceAsReader(String name, String encoding) {
        try {
            return new InputStreamReader(getResourceAsStream(name), encoding);
        } catch (UnsupportedEncodingException e) {
            throw new ResourceNotFoundException("Encoding " + encoding + " for resources " + name + " not supported", e);
        }
    }

    /**
     * Add a ResourceLocator to the list of locators to search for resources from.
     *
     * @param resourceLocator The ResourceLocator to add to the search path.
     */
    public void addResourceLocator(ResourceLocator resourceLocator) {
        resourceLocators.add(resourceLocator);
    }

    /**
     * Remove a ResourceLocator from the list of locators to search for resources.
     *
     * @param resourceLocator The ResourceLocator to remove from the list.
     */

    public void removeResourceLocator(ResourceLocator resourceLocator) {
        resourceLocators.remove(resourceLocator);
    }

    /**
     * Set the list of resource locators to search resources from.
     *
     * @param resourceLocators The list of resource locators to search from.
     */
    public void setResourceLocators(List<ResourceLocator> resourceLocators) {
        this.resourceLocators = resourceLocators;
    }

}
