package org.softuni.casebook.template_engine;

import org.softuni.casebook.constants.CasebookConstants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LimeLeafImpl implements LimeLeaf {
    private Map<String, String> viewData;

    public LimeLeafImpl() {
        initializeViewDataMap();
    }

    private void initializeViewDataMap() {
        this.viewData = new HashMap<>();
        this.addAttributeToViewData("error", "");
        this.addAttributeToViewData("success", "");
        this.addAttributeToViewData("warning", "");
    }

    @Override
    public Map<String, String> getViewData() {
        return Collections.unmodifiableMap(this.viewData);
    }

    @Override
    public void clearNotificationsMessages() {
        this.addAttributeToViewData("error", "");
        this.addAttributeToViewData("success", "");
        this.addAttributeToViewData("warning", "");
    }

    @Override
    public String renderHtml(String html) {
        if (CasebookConstants.EMPTY_STRING.equals(html)) {
            return html;
        }
        for (var viewDataObject : this.viewData.entrySet()) {
            html = html.replace(
                    "{{" + viewDataObject.getKey() + "}}"
                    , viewDataObject.getValue());
        }

        return html;
    }

    @Override
    public void addAttributeToViewData(String key, String data) {
        this.viewData.put(key, data);
    }
}
