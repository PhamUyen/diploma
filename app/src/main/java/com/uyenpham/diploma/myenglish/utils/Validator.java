package com.uyenpham.diploma.myenglish.utils;


public class Validator {

    private static final String PHONE_REGEX = "(\\+[0-9]+[\\- \\.]*)?(\\([0-9]+\\)[\\- \\.]*)?([0-9][0-9\\- \\.]+[0-9])";
    private static final String EMAIL_REGEX = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+";
    private static final String IP_REGEX = "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9]))";

    /**
     * check phone number is valid or not
     *
     * @param phoneNumber phone number
     * @return valid or not
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        } else {
            return phoneNumber.matches(PHONE_REGEX);
        }
    }

    /**
     * check email address is valid or not
     *
     * @param emailAddress email address
     * @return valid or not
     */
    public static boolean isValidEmailAddress(String emailAddress) {
        if (emailAddress == null || emailAddress.isEmpty()) {
            return false;
        } else {
            return emailAddress.matches(EMAIL_REGEX);
        }
    }

    /**
     * check ip address is valid or not
     *
     * @param ipAddress ip address
     * @return valid or not
     */
    public static boolean isValidIpAddress(String ipAddress) {
        if (ipAddress == null || ipAddress.isEmpty()) {
            return false;
        } else {
            return ipAddress.matches(IP_REGEX);
        }
    }

    /**
     * check string is empty or not
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
