package server.utils;

public class StringUtils {

    public static String format(String string, String token, Object... args) {
        for (int i = 0; i < args.length; i++) {
            string = string.replace(token + (i + 1), args[i].toString());
        }
        return string;
    }

    public static boolean isSimilarWord(String to, String from, double similarPercent) {
        char[] max;
        char[] min;
        if (to.length() > from.length()) {
            max = to.toCharArray();
            min = from.toCharArray();
        } else {
            min = to.toCharArray();
            max = from.toCharArray();
        }
        int matchs = 0;
        for (int i = 0; i < min.length; i++) {
            final char c1 = Character.toUpperCase(max[i]);
            final char c2 = Character.toUpperCase(min[i]);
            if (c1 == c2) matchs++;
        }
        return 100f * matchs / from.length() >= similarPercent;
    }

}
