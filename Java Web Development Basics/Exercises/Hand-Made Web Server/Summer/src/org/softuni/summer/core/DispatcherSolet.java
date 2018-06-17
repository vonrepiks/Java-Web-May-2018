package org.softuni.summer.core;

import org.softuni.broccolina.solet.*;
import org.softuni.javache.http.HttpSession;
import org.softuni.javache.http.HttpStatus;
import org.softuni.summer.SummerConstants;
import org.softuni.summer.api.Model;
import org.softuni.summer.api.PathVariable;
import org.softuni.summer.utility.ControllerActionPair;
import org.softuni.summer.utility.ControllerLoadingService;
import org.softuni.summer.utility.TemplateEngine;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@WebSolet(route = "/*")
public class DispatcherSolet extends BaseHttpSolet {
    private String applicationClassesFolderPath;

    private DependencyContainer dependencyContainer;

    private ControllerLoadingService controllerLoadingService;

    private TemplateEngine templateEngine;

    private ControllerActionInvoker controllerActionInvoker;

    private ControllerActionPair getControllerActionPairCandidate(HttpSoletRequest httpSoletRequest) {
        Set<Object> actionParameters = new LinkedHashSet<>();

        ControllerActionPair candidateCap = this.controllerLoadingService
                .getLoadedControllersAndActions()
                .get(httpSoletRequest.getMethod())
                .entrySet()
                .stream()
                .filter(action -> {
                    Pattern routePattern = Pattern.compile(action.getKey());
                    Matcher routeMatcher = routePattern.matcher(httpSoletRequest.getRequestUrl());

                    if (routeMatcher.find()) {
                        List<Parameter> pathVariables = Arrays.stream(action.getValue()
                                .getAction().getParameters())
                                .filter(parameter -> parameter.isAnnotationPresent(PathVariable.class))
                                .collect(Collectors.toList());

                        for (Parameter pathVariable : pathVariables) {
                            String variableName = pathVariable.getAnnotation(PathVariable.class).name();

                            String variableValue = routeMatcher.group(variableName);

                            actionParameters.add(variableValue);
                        }
                        return true;
                    } else {
                        return false;
                    }
                })
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);

        for (Object actionParameter : actionParameters) {
            candidateCap.addParameter(actionParameter);
        }

        return candidateCap;
    }

    private void handleRequest(HttpSoletRequest httpSoletRequest, HttpSoletResponse httpSoletResponse) {
        ControllerActionPair cap = this.getControllerActionPairCandidate(httpSoletRequest);
        System.out.println(cap);
        if (cap == null) {
            if (httpSoletRequest.getMethod().equals(SummerConstants.GET_REQUEST_METHOD_NAME)) {
                super.doGet(httpSoletRequest, httpSoletResponse);
            } else if (httpSoletRequest.getMethod().equals(SummerConstants.POST_REQUEST_METHOD_NAME)) {
                super.doPost(httpSoletRequest, httpSoletResponse);
            }
            return;
        }

        try {
            String result = this.controllerActionInvoker
                    .invokeAction(cap)
                    .toString();

            System.out.println(result);

            httpSoletResponse.setStatusCode(HttpStatus.OK);
            httpSoletResponse.addHeader("Content-Type", "text/html");

            if (result.startsWith("template:")) {
                String templateName = result.split(":")[1];
                System.out.println(templateName);
                httpSoletResponse.setContent(this.templateEngine.loadTemplate(templateName, (Model) this.dependencyContainer.getObject(Model.class.getSimpleName())).getBytes());
            } else if (result.startsWith("redirect:")) {
                String route = result.split(":")[1];

                httpSoletResponse.setStatusCode(HttpStatus.SEE_OTHER);
                httpSoletResponse.addHeader("Location", route);
            } else {
                httpSoletResponse.setContent(result.getBytes());
            }
        } catch (IllegalAccessException | InvocationTargetException | IOException | NoSuchMethodException | InstantiationException e) {
            httpSoletResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            StringBuilder content = new StringBuilder();
            content.append("<h1>" + e.getMessage() + "</h1><p>");
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                content.append("<div>" + stackTraceElement.toString() + "</div>");
            }
            content.append("</p>");
            httpSoletResponse.setContent(content.toString().getBytes());
        }
    }

    @Override
    public void init(SoletConfig soletConfig) {
        super.init(soletConfig);

        this.applicationClassesFolderPath = soletConfig.getAttribute("application-folder") + "classes" + File.separator;

        this.dependencyContainer = new DependencyContainer();
        this.controllerLoadingService = new ControllerLoadingService();
        this.templateEngine = new TemplateEngine(this.applicationClassesFolderPath
                + "resources"
                + File.separator
                + "templates"
                + File.separator);
        this.controllerActionInvoker = new ControllerActionInvoker(this.dependencyContainer);

        try {
            this.controllerLoadingService.loadControllersActionHandlers(this.applicationClassesFolderPath);
        } catch (NoSuchMethodException | IOException | InstantiationException | IllegalAccessException | ClassNotFoundException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void service(HttpSoletRequest httpSoletRequest, HttpSoletResponse httpSoletResponse) throws IOException {
        this.dependencyContainer.addInstantiatedObject(HttpSoletRequest.class.getSimpleName(), httpSoletRequest);
        this.dependencyContainer.addInstantiatedObject(HttpSoletResponse.class.getSimpleName(), httpSoletResponse);
        if (httpSoletRequest.getSession() != null) {
            this.dependencyContainer.addInstantiatedObject(HttpSession.class.getSimpleName(), httpSoletRequest.getSession());
        }

        super.service(httpSoletRequest, httpSoletResponse);

        this.dependencyContainer.evictCachedStaticStates();
    }

    @Override
    protected void doGet(HttpSoletRequest httpSoletRequest, HttpSoletResponse httpSoletResponse) {
        System.out.println("GET");
        this.handleRequest(httpSoletRequest, httpSoletResponse);
    }

    @Override
    protected void doPost(HttpSoletRequest httpSoletRequest, HttpSoletResponse httpSoletResponse) {
        this.handleRequest(httpSoletRequest, httpSoletResponse);
    }
}
