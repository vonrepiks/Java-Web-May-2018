package org.softuni.casebook.utility;

import org.softuni.casebook.template_engine.LimeLeafImpl;

public interface Notification {
    String getError(LimeLeafImpl limeLeaf);

    String getSuccess(LimeLeafImpl limeLeaf);

    String getWarning(LimeLeafImpl limeLeaf);
}
