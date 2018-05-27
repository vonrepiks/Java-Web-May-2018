package org.softuni.casebook.utility;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LimeLeaf {
    private static LimeLeaf limeLeaf = null;

    private static Map<String, String> viewData;

    private LimeLeaf() {
        initializeViewDataMap();
    }

    private void initializeViewDataMap() {
        viewData = new HashMap<>();
        addAttributeToViewData("error", "");
        addAttributeToViewData("success", "");
    }

    public String renderHtml(String html) {
        for (var viewDataObject : viewData.entrySet()) {
            html = html.replace(
                    "{{" + viewDataObject.getKey() + "}}"
                    , viewDataObject.getValue());
        }

        return html;
    }

    public void addAttributeToViewData(String key, String data) {
        viewData.put(key, data);
    }

    public Map<String, String> getViewData() {
        return Collections.unmodifiableMap(viewData);
    }

    public static LimeLeaf getInstance( ) {
        if(limeLeaf == null) {
            limeLeaf = new LimeLeaf();
        }
        return limeLeaf;
    }
}
