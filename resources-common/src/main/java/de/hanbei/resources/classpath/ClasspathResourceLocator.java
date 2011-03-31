package de.hanbei.resources.classpath;

import de.hanbei.resources.ResourceLocator;

import java.io.InputStream;

/** @author fschulz */
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
