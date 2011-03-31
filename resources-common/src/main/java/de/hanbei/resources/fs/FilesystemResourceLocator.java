package de.hanbei.resources.fs;

import de.hanbei.resources.ResourceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/** @author fschulz */
public class FilesystemResourceLocator implements ResourceLocator {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilesystemResourceLocator.class);

    private File resourceBase;

    public FilesystemResourceLocator() {
        this(new File("."));
    }

    public FilesystemResourceLocator(String base) {
        this(new File(base));
    }

    public FilesystemResourceLocator(File base) {
        this.resourceBase = base;
        LOGGER.info("Using {} as resource base.", resourceBase.getAbsolutePath());
    }

    public InputStream getResource(String name) {
        File file = null;
        try {
            file = new File(this.resourceBase, name);
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            LOGGER.warn("Resource {} not found", name);
        }
        return null;
    }


}
