package org.softuni.casebook.template_engine;

import java.util.Map;

public interface LimeLeaf {
    Map<String, String> getViewData();

    void clearNotificationsMessages();

    String renderHtml(String html);

    void addAttributeToViewData(String key, String data);
}
