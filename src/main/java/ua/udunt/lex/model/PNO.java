package ua.udunt.lex.model;

public enum PNO {

    MC("Mastercard"),
    VISA("Visa"),
    UPI("UnionPay"),
    AMEX("American Express"),
    PROSTIR("Prostir"),
    EMV("EMVCo"),
    PRIVATBANK("Privatbank"),
    UNKNOWN("Unknown");

    private String canonicalName;

    PNO(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    public static PNO fromPan(String pan) {
        if (pan == null) {
            return UNKNOWN;
        }
        if (pan.matches("[4]\\d{12,18}")) {
            return VISA;
        } else if (pan.matches("62\\d{14,17}")) {
            return UPI;
        } else if (pan.matches("(2\\d{15,18})|(5[1-5]\\d{14,17})")) {
            return MC;
        } else if (pan.matches("(50)|(5[6-8])|(6[0-9])\\d{10,17}")) {
            return MC;
        } else if (pan.matches("(34)|(37)\\d{13}")) {
            return AMEX;
        } else if (pan.matches("[9]\\d{15}")) {
            return PROSTIR;
        } else if (pan.matches("[7]\\d{15}")) {
            return PRIVATBANK;
        } else {
            return UNKNOWN;
        }
    }

    public String getCanonicalName() {
        return canonicalName;
    }

}
