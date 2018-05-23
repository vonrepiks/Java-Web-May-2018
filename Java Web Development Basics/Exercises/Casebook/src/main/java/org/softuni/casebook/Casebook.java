package org.softuni.casebook;

import org.softuni.casebook.controllers.BaseController;
import org.softuni.casebook.controllers.ResourceController;
import org.softuni.casebook.routes.ControllerMethodEntry;
import org.softuni.casebook.routes.RoutesManager;
import org.softuni.javache.api.RequestHandler;
import org.softuni.javache.http.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class Casebook implements RequestHandler {
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;
    private HttpSessionStorageImpl sessionStorage;
    private boolean hasIntercepted;
    private RoutesManager routesManager;

    public Casebook(HttpSessionStorageImpl sessionStorage, RoutesManager routesManager) {
        this.sessionStorage = sessionStorage;
        this.hasIntercepted = false;
        this.routesManager = routesManager;
        this.routesManager.initializeRoots();
    }

    private byte[] processGetRequest() throws InvocationTargetException, IllegalAccessException {
        String url = this.httpRequest.getRequestUrl();
        Map<String, ControllerMethodEntry<Method, BaseController>> getMappingRoutes = this.routesManager.getGetMappingRoutes();
        if (getMappingRoutes.containsKey(url)) {
            ControllerMethodEntry<Method, BaseController> entry = getMappingRoutes.get(url);
            return (byte[]) entry.getKey().invoke(entry.getValue(), this.httpResponse);
        }
//        if(url.equals("/")) {
//            //INDEX
//
//            return new HomeController(this.httpRequest, this.httpResponse).index("/index");
//        } else if (url.equals("/login")) {
//            //LOGIN
//
//            return new UserController(this.httpRequest, this.httpResponse).login(url);
//        }


//        else if (this.httpRequest.getRequestUrl().equals("/login")) {
//            //LOGIN
//            HttpSession session = new HttpSessionImpl();
//            session.addAttribute("username", "Pesho");
//
//            this.sessionStorage.addSession(session);
//
//            this.httpResponse.addCookie("Javache", session.getId());
//            return this.processPageRequest(this.httpRequest.getRequestUrl());
//        } else if (this.httpRequest.getRequestUrl().equals("/logout")) {
//            //LOGOUT
//
//            if(!this.httpRequest.getCookies().containsKey("Javache")) {
//                return this.redirect(("You must login to access this route!").getBytes()
//                        , "/");
//            }
//
//            String sessionId = this.httpRequest.getCookies().get("Javache").getValue();
//            this.sessionStorage.getById(sessionId).invalidate();
//
//            this.httpResponse.addCookie("Javache", "deleted; expires=Thu, 01 Jan 1970 00:00:00 GMT;");
//
//            return this.ok(("Successfully expired").getBytes());
//        } else if (this.httpRequest.getRequestUrl().equals("/forbidden")) {
//            //FORBIDDEN
//
//            if(!this.httpRequest.getCookies().containsKey("Javache")) {
//                return this.redirect(("You must login to access this route!").getBytes()
//                        , "/");
//            }
//
//            String sessionId = this.httpRequest.getCookies().get("Javache").getValue();
//            HttpSession session = this.sessionStorage.getById(sessionId);
//            String username = session.getAttributes().get("username").toString();
//
//            return this.ok(("HELLO " + username + "!!!").getBytes());
//        }

        return new ResourceController().processResourceRequest(this.httpRequest, this.httpResponse);
    }

    @Override
    public byte[] handleRequest(String requestContent) throws InvocationTargetException, IllegalAccessException {
        this.httpRequest = new HttpRequestImpl(requestContent);
        this.httpResponse = new HttpResponseImpl();

        byte[] result = null;

        if(this.httpRequest.getMethod().equals("GET")) {
            result = this.processGetRequest();
        }

        this.sessionStorage.refreshSessions();

        this.hasIntercepted = true;

        return result;
    }

    @Override
    public boolean hasIntercepted() {
        return this.hasIntercepted;
    }
}
