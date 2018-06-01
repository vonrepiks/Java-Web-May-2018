package org.softuni.casebook.constants;

public final class CasebookConstants {

    public static final String ROOT_FOLDER = System.getProperty("user.dir");

    private static final String CASEBOOK_RESOURCE_PATH = ROOT_FOLDER + "\\src\\main\\java\\org\\softuni\\casebook\\resources\\";

    private static final String HEADERS_PATH = CASEBOOK_RESOURCE_PATH + "headers\\";

    public static final String FOOTER_PATH = CASEBOOK_RESOURCE_PATH + "footer\\";

    public static final String PUBLIC_RESOURCES_PATH = CASEBOOK_RESOURCE_PATH + "public\\";

    public static final String TEMPLATES_PATH = CASEBOOK_RESOURCE_PATH + "templates\\";

    public static final String NOTIFICATIONS_PATH = CASEBOOK_RESOURCE_PATH + "notifications\\";

    public static final String USER_HEADER_HTML = HEADERS_PATH + "header-user.html";

    public static final String GUEST_HEADER_HTML = HEADERS_PATH + "header-guest.html";

    public static final String BASE_VIEW_HTML = CASEBOOK_RESOURCE_PATH + "base-template.html";

    public static final String SERVER_SESSION_TOKEN = "Javache";

    public static final String ERROR_MESSAGE_TYPE = "error";

    public static final String SUCCESS_MESSAGE_TYPE = "success";

    public static final String WARNING_MESSAGE_TYPE = "warning";

    public static final String HTML_EXTENSION_AND_SEPARATOR = ".html";

    public static final String EMPTY_STRING = "";

    private CasebookConstants() {}
}
