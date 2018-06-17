package org.softuni.broccolina;

import org.softuni.broccolina.solet.*;
import org.softuni.broccolina.utility.ApplicationLoadingService;
import org.softuni.javache.api.RequestHandler;
import org.softuni.javache.io.Reader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SoletDispatcher implements RequestHandler {
    private static final String WEBAPPS_FOLDER_NAME = "webapps";

    private final String serverRootFolderPath;
    private boolean hasIntercepted;
    private Map<String, HttpSolet> httpSoletMap;
    private ApplicationLoadingService applicationLoadingService;

    public SoletDispatcher(String serverRootFolderPath) {
        this.serverRootFolderPath = serverRootFolderPath;
        this.hasIntercepted = false;
        this.applicationLoadingService = new ApplicationLoadingService();
        this.initializeSoletMap();
    }

    private void initializeSoletMap() {
        try {
            this.httpSoletMap = this.applicationLoadingService.loadApplications(serverRootFolderPath + WEBAPPS_FOLDER_NAME + File.separator);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HttpSolet findSoletCandidate(HttpSoletRequest httpSoletRequest, HttpSoletResponse httpSoletResponse) {
        String requestUrl = httpSoletRequest.getRequestUrl();

        Pattern applicationRouteMatchPattern = Pattern.compile("/[a-zA-Z0-9]+/");
        Matcher applicationRouteMatcher = applicationRouteMatchPattern.matcher(requestUrl);

        String route = null;

        if (this.httpSoletMap.containsKey(requestUrl)) {
            route = requestUrl;
        }

        if (applicationRouteMatcher.find()) {
            String applicationRoute = applicationRouteMatcher.group() + "*";
            if (this.httpSoletMap.containsKey(applicationRoute)) {
                route = applicationRoute;
            }
        }

        if (this.httpSoletMap.containsKey("/*")) {
            route = "/*";
            this.httpSoletMap.get("/*").init(null);
        }

        return this.httpSoletMap.get(route);
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream) {
        try {
            HttpSoletRequest httpSoletRequest = new HttpSoletRequestImpl(Reader.readAllLines(inputStream), inputStream);
            HttpSoletResponse httpSoletResponse = new HttpSoletResponseImpl(outputStream);

            HttpSolet httpSoletObject = this.findSoletCandidate(httpSoletRequest, httpSoletResponse);

            if (httpSoletRequest.isResource() || httpSoletObject == null) {
                this.hasIntercepted = false;
                return;
            }

            Class[] soletServiceMethodParameters = Arrays.stream(httpSoletObject.getClass().getMethods())
                    .filter(x -> x.getName().equals("service"))
                    .findFirst()
                    .orElse(null)
                    .getParameterTypes();

            httpSoletObject
            .getClass()
            .getMethod("service", soletServiceMethodParameters[0], soletServiceMethodParameters[1])
            .invoke(httpSoletObject, httpSoletRequest, httpSoletResponse);

            this.hasIntercepted = true;
        } catch (IOException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            this.hasIntercepted = false;
        }
    }

    @Override
    public boolean hasIntercepted() {
        return this.hasIntercepted;
    }
}
