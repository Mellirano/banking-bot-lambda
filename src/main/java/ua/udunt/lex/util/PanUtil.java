package ua.udunt.lex.util;

import ua.udunt.lex.model.PNO;

import java.util.Optional;

public class PanUtil {

    public static boolean luhnValidate(String pan) {
        if (!Optional.ofNullable(pan).orElse("").matches("\\d+")) {
            return false;
        }
        int sum = 0;
        boolean alternate = false;
        for (int i = pan.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(pan.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

    public static boolean isValidPan(String pan) {
        return !LibUtil.isNullOrEmptyAll(pan) && PNO.fromPan(pan) != PNO.UNKNOWN && luhnValidate(pan);
    }

}
