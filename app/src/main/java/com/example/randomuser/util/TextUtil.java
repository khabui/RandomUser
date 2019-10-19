package com.example.randomuser.util;

public class TextUtil {

    public static String dateAndTime(String inputString) {
        String date = inputString.split("T")[0];
        String time = inputString.split("T")[1].split("Z")[0];

        return (date + " " + time);
    }

    public static String toTitleCase(String string) {
        if (string == null) {
            return null;
        }

        boolean space = true;
        StringBuilder builder = new StringBuilder(string);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }
}
