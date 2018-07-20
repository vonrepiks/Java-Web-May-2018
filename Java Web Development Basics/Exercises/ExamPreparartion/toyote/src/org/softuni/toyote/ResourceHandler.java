package org.softuni.toyote;

import org.softuni.javache.api.RequestHandler;
import org.softuni.javache.http.*;
import org.softuni.javache.io.Reader;
import org.softuni.javache.io.Writer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ResourceHandler implements RequestHandler {
    private static final String APPLICATION_RESOURCES_FOLDER_NAME = "resources";

    private static final String RESOURCE_NOT_FOUND_MESSAGE = "<h1 style=\"text-align: center;\">The resource - \"%s\" you are looking for cannot be found.</h1>";

    private static final String ROOT_APPLICATION_NAME = "ROOT";

    private static final String APPLICATIONS_FOLDER = "webapps";

    private static final String JAR_EXTENSION = ".jar";

    private final String serverRootFolderPath;

    private List<String> applicationsNames;

    private boolean hasIntercepted;

    public ResourceHandler(String serverRootFolderPath) {
        this.serverRootFolderPath = serverRootFolderPath;
        this.seedApplicationsNames(
                this.serverRootFolderPath
                        + APPLICATIONS_FOLDER
                        + File.separator);
        this.hasIntercepted = false;
    }

    private boolean isJarFile(File file) {
        return file.isFile() && file.getName().endsWith(JAR_EXTENSION);
    }

    //TODO: FIX BUG WITH APPLICATION NAME
    //TODO: METHOD SHOULD NOT DEPEND ON REQUEST URL BUT ON APPLICATION NAME
    private void seedApplicationsNames(String applicationsFolderPath) {
        this.applicationsNames = new ArrayList<>();

        File applicationsFolder = new File(applicationsFolderPath);

        if (applicationsFolder.exists() && applicationsFolder.isDirectory()) {
            List<File> allJarFiles = Arrays.stream(applicationsFolder.listFiles())
                    .filter(this::isJarFile)
                    .collect(Collectors.toList());

            for (File applicationJarFile : allJarFiles) {
                String applicationNameCandidate = applicationJarFile.getName().replace(JAR_EXTENSION, "");

                if (!applicationNameCandidate.equals(ROOT_APPLICATION_NAME)) {
                    this.applicationsNames.add(applicationNameCandidate);
                }
            }
        }
    }

    private String getApplicationName(String requestUrl) {
        String subRequestUrl = requestUrl.replaceFirst("/", "");
        String applicationNameCandidate = subRequestUrl.substring(0, subRequestUrl.indexOf("/"));
        if (this.applicationsNames.contains(applicationNameCandidate)) {
            return applicationNameCandidate;
        }

        return ROOT_APPLICATION_NAME;
    }

    private String getResourceName(String requestUrl) {
        return requestUrl.substring(requestUrl.lastIndexOf("/") + 1);
    }

    private void notFound(String resourceName, HttpResponse response) {
        response.setStatusCode(HttpStatus.NOT_FOUND);

        response.addHeader("Content-Type", "text/html");

        response.setContent(String.format(RESOURCE_NOT_FOUND_MESSAGE, resourceName).getBytes());
    }

    private String getResourcePath(String resourcesFolder, String resourceName, String root) throws IOException {
        File currentFolder = new File(resourcesFolder);

        if (!currentFolder.isDirectory()) {
            return null;
        }

        for (File file : currentFolder.listFiles()) {
            if (file.isDirectory()) {
                return this.getResourcePath(file.getCanonicalPath(), resourceName, root + File.separator + file.getName());
            }

            if (file.getName().equals(resourceName)) {
                return root;
            }
        }

        return "/";
    }

    private void handleResourceRequest(String resourcesFolder, String resourceName, HttpResponse response) {
        try {
            String resourceCandidatePath = this.getResourcePath(resourcesFolder, resourceName, "");

            Path resourcePath = Paths.get(resourcesFolder + resourceCandidatePath + File.separator + resourceName);

            byte[] resourceContent = Files.readAllBytes(resourcePath);

            response.setStatusCode(HttpStatus.OK);

            response.addHeader("Content-Type", Files.probeContentType(resourcePath));
            response.addHeader("Content-Length", resourceContent.length + "");
            response.addHeader("Content-Disposition", "inline");

            response.setContent(resourceContent);
        } catch (IOException e) {
            this.notFound(resourceName, response);
        }
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream) {
        try {
            HttpRequest request = new HttpRequestImpl(new Reader().readAllLines(inputStream));
            HttpResponse response = new HttpResponseImpl();

            String applicationName = this.getApplicationName(request.getRequestUrl());

            String resourcesFolder = this.serverRootFolderPath
                    + APPLICATIONS_FOLDER
                    + File.separator
                    + applicationName
                    + File.separator
                    + APPLICATION_RESOURCES_FOLDER_NAME;

            String resourceName = this.getResourceName(request.getRequestUrl());

            this.handleResourceRequest(resourcesFolder, resourceName, response);

            new Writer().writeBytes(response.getBytes(), outputStream);
            this.hasIntercepted = true;
        } catch (IOException e) {
            e.printStackTrace();
            this.hasIntercepted = false;
        }
    }

    @Override
    public boolean hasIntercepted() {
        return this.hasIntercepted;
    }
}
