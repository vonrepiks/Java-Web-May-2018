package org.softuni.javache.utility;

import org.softuni.javache.WebConstants;
import org.softuni.javache.io.Reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class JavacheConfigService {
    private static final String CONFIG_FOLDER_PATH =
            WebConstants.SERVER_ROOT_FOLDER_PATH
            + "config/";

    private static final String REQUEST_HANDLER_PRIORITY_CONFIG_FILE_PATH =
            CONFIG_FOLDER_PATH
            + "config.ini";

    private static final String PRIORITY_CONFIG_FILE_NOT_EXIST_EXCEPTION_MESSAGE = "Request handler priority configuration file does not exist!";

    private Set<String> requestHandlersPriority;

    public JavacheConfigService() {
        this.initConfigurations();
    }

    private void loadRequestHandlersConfig() throws IOException {
        File priorityConfigFile = new File(REQUEST_HANDLER_PRIORITY_CONFIG_FILE_PATH);

        if (!priorityConfigFile.exists() || !priorityConfigFile.isFile()) {
            throw new IllegalArgumentException(PRIORITY_CONFIG_FILE_NOT_EXIST_EXCEPTION_MESSAGE);
        }

        String configFileContent = Reader.readAllLines(new FileInputStream(priorityConfigFile));

        this.requestHandlersPriority = Arrays.stream(configFileContent.replace("request-handlers: ", "").split(",")).collect(Collectors.toSet());
    }

    private void initConfigurations() {
        try {
            this.loadRequestHandlersConfig();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public Set<String> getRequestHandlersPriority() {
        return Collections.unmodifiableSet(this.requestHandlersPriority);
    }
}
