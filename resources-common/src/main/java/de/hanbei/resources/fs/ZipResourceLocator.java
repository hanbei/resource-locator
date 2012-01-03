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
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author fschulz
 */
public class ZipResourceLocator implements ResourceLocator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZipResourceLocator.class);

    private ZipFile zipFile;

    public ZipResourceLocator(File file) {
        try {
            zipFile = new ZipFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InputStream getResource(String name) {
        ZipEntry zipEntry = zipFile.getEntry(name);
        if (zipEntry == null) {
            LOGGER.warn("Resource {} not found in zip " + zipFile.getName(), name);
            return null;
        }

        try {
            return zipFile.getInputStream(zipEntry);
        } catch (IOException e) {
            LOGGER.warn("Resource {} not found in zip " + zipFile.getName(), name);
        }
        return null;
    }

}
