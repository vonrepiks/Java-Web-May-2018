package org.softuni.casebook.utility;

import org.softuni.casebook.constants.CasebookConstants;
import org.softuni.casebook.template_engine.LimeLeafImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.softuni.casebook.constants.CasebookConstants.HTML_EXTENSION_AND_SEPARATOR;

public class NotificationImpl implements Notification {
    private String error;
    private String success;
    private String warning;

    public NotificationImpl () {
        this.error = "";
        this.success = "";
        this.warning = "";
    }

    private String getMessage(String type, LimeLeafImpl limeLeaf) {
        try {
            if (!limeLeaf.getViewData().get(type).equals("")) {
                List<String> view = Files.readAllLines(Paths.get(CasebookConstants.NOTIFICATIONS_PATH + type + HTML_EXTENSION_AND_SEPARATOR));
                return String.join("", view);
            }
            return CasebookConstants.EMPTY_STRING;
        } catch (IOException e) {
            return CasebookConstants.EMPTY_STRING;
        }
    }

    @Override
    public String getError(LimeLeafImpl limeLeaf) {
        return this.getMessage(CasebookConstants.ERROR_MESSAGE_TYPE, limeLeaf);
    }

    @Override
    public String getSuccess(LimeLeafImpl limeLeaf) {
        return this.getMessage(CasebookConstants.SUCCESS_MESSAGE_TYPE, limeLeaf);
    }

    @Override
    public String getWarning(LimeLeafImpl limeLeaf) {
        return this.getMessage(CasebookConstants.WARNING_MESSAGE_TYPE, limeLeaf);
    }
}
