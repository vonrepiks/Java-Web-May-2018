package javache.utility;

public final class Helpers {
    private Helpers() {}

    public static String getExtension(String url) {
        return url.substring(url.lastIndexOf(".") + 1);
    }
}
