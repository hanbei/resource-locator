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
package de.hanbei.resources.fs;

import de.hanbei.resources.ResourceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author fschulz
 */
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
