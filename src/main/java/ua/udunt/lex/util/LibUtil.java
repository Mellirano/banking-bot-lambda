package ua.udunt.lex.util;

import java.util.Collection;

public class LibUtil {

    public static boolean isNullOrEmpty(Object val) {
        return val == null
                || (val instanceof String && ((String) val).isBlank())
                || (val instanceof Collection && ((Collection) val).size() == 0);
    }

    public static boolean isNullOrEmptyOneOf(Object... vals) {
        for (Object val : vals) {
            if (isNullOrEmpty(val)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNullOrEmptyAll(Object... vals) {
        for (Object val : vals) {
            if (!isNullOrEmpty(val)) {
                return false;
            }
        }
        return true;
    }

}
