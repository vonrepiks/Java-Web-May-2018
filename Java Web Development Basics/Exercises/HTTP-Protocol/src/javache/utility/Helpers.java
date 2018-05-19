package javache.utility;

public final class Helpers {
    private Helpers() {}

    public static String getExtension(String url) {
        int index = url.lastIndexOf(".");
        if (index == -1) {
            return "";
        }
        return url.substring(url.lastIndexOf(".") + 1);
    }
}
