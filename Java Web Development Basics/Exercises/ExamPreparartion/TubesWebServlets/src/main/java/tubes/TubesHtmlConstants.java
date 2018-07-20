package tubes;

public final class TubesHtmlConstants {

    public static final String HOME_TUBE_PAGE = "<h1>Welcome to servlets tube example</h1>\n" +
            "<hr />\n" +
            "<a href=\"/tubes/create\">Create tube</a>";

    public static final String CREATE_TUBE_FORM = "<h1>Create tube</h1>\n" +
            "<hr/>\n" +
            "<form method=\"post\" action=\"/tubes/details\">\n" +
            "    <div>\n" +
            "        <label for=\"title\">Title:</label>\n" +
            "        <input type=\"text\" id=\"title\" name=\"title\" placeholder=\"Title...\"/>\n" +
            "    </div>\n" +
            "    <hr/>\n" +
            "    <div>\n" +
            "        <label for=\"description\">Description:</label>\n" +
            "        <input type=\"text\" id=\"description\" name=\"description\" placeholder=\"Description...\"/>\n" +
            "    </div>\n" +
            "    <hr/>\n" +
            "    <div>\n" +
            "        <label for=\"videoLink\">Video Link:</label>\n" +
            "        <input type=\"text\" id=\"videoLink\" name=\"videoLink\" placeholder=\"Video Link...\"/>\n" +
            "    </div>\n" +
            "    <hr/>\n" +
            "    <input type=\"submit\" value=\"Create Tube\">\n" +
            "</form>";

    private TubesHtmlConstants() {}
}
