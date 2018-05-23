package org.softuni;

import org.softuni.casebook.Casebook;
import org.softuni.casebook.routes.RoutesManager;
import org.softuni.casebook.routes.RoutesManagerImpl;
import org.softuni.javache.Server;
import org.softuni.javache.WebConstants;
import org.softuni.javache.api.RequestHandler;
import org.softuni.javache.http.HttpSessionStorageImpl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class StartUp {
    public static void main(String[] args) {
        start(args);
    }

    private static HttpSessionStorageImpl getSessionStorage() {
        return new HttpSessionStorageImpl();
    }

    private static RoutesManager getRoutesManager() {
        return new RoutesManagerImpl();
    }

    private static Set<RequestHandler> initializeApplications() {
        return new HashSet<>() {{
            add(new Casebook(getSessionStorage(), getRoutesManager()));
        }};
    }

    private static void start(String[] args) {
        int port = WebConstants.DEFAULT_SERVER_PORT;

        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        }

        Set<RequestHandler> applications = initializeApplications();

        Server server = new Server(port, applications);

        try {
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
