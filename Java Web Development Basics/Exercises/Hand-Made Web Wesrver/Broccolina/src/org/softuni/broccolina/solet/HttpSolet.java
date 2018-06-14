package org.softuni.broccolina.solet;

public interface HttpSolet {

    void init(SoletConfig soletConfig);

    SoletConfig getSoletConfig();

    void service(HttpSoletRequest httpSoletRequest, HttpSoletResponse httpSoletResponse);
}
