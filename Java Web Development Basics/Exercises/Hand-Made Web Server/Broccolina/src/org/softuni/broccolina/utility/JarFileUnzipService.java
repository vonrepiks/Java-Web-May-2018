package org.softuni.broccolina.utility;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarFileUnzipService {
    private void deleteFolder(File jarFolder) {
        for (File file : jarFolder.listFiles()) {
            if (file.isDirectory()) {
                this.deleteFolder(file);
            }

            file.delete();
        }
    }

    public void unzipJar(File jarFile) throws IOException {
        String rootCanonicalPath = jarFile.getCanonicalPath();

        JarFile fileAsJarArchive = new JarFile(rootCanonicalPath);

        Enumeration<JarEntry> jarEntries = fileAsJarArchive.entries();

        File jarFolder = new File(rootCanonicalPath.replace(".jar", ""));

        if (jarFolder.exists() && jarFolder.isDirectory()) {
            this.deleteFolder(jarFolder);
        }

        jarFolder.mkdir();

        while (jarEntries.hasMoreElements()) {
            JarEntry currentEntry = jarEntries.nextElement();

            String currentEntryCanonicalPath = jarFolder.getCanonicalPath() + File.separator + currentEntry.getRealName();
            File currentEntryAsFile = new File(currentEntryCanonicalPath);

            if (currentEntry.isDirectory()) {
                currentEntryAsFile.mkdir();
                continue;
            }

            InputStream currentEntryInputStream = fileAsJarArchive.getInputStream(currentEntry);
            OutputStream currentEntryOutputStream = new FileOutputStream(currentEntryAsFile.getCanonicalPath());

            while (currentEntryInputStream.available() > 0) {
                currentEntryOutputStream.write(currentEntryInputStream.read());
            }

            currentEntryInputStream.close();
            currentEntryOutputStream.close();

        }

        fileAsJarArchive.close();
    }
}
