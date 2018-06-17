package org.softuni.broccolina.solet;

import java.io.IOException;

public interface HttpSolet {

    void init(SoletConfig soletConfig);

    boolean isInitialized();

    SoletConfig getSoletConfig();

    void service(HttpSoletRequest httpSoletRequest, HttpSoletResponse httpSoletResponse) throws IOException;
}
