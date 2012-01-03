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
package de.hanbei.resources.classpath;

import de.hanbei.resources.ResourceLocator;

import java.io.InputStream;

/**
 * @author fschulz
 */
public class ClasspathResourceLocator implements ResourceLocator {

    private final ClassLoader resourceClassLoader;

    public ClasspathResourceLocator() {
        this.resourceClassLoader = this.getClass().getClassLoader();
    }

    public ClasspathResourceLocator(ClassLoader classLoader) {
        this.resourceClassLoader = classLoader;
    }

    public ClasspathResourceLocator(Class<?> classToLoadFrom) {
        this.resourceClassLoader = classToLoadFrom.getClassLoader();
    }

    public InputStream getResource(String name) {
        InputStream resourceAsStream = this.resourceClassLoader.getResourceAsStream(name);
        return resourceAsStream;
    }


}
