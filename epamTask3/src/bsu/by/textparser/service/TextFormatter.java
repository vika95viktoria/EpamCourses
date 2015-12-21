package bsu.by.textparser.service;

/**
 * Created by Виктория on 21.12.2015.
 */
public class TextFormatter {
    private static final String COMA_REGEX = "\\s[,]";
    private static final String DOT_REGEX = "\\s[.]";
    private static final String COLON_REGEX = "\\s[:]";

    public static String formatText(String textInfo) {
        textInfo = textInfo.replaceAll(COMA_REGEX, ",");
        textInfo = textInfo.replaceAll(DOT_REGEX, ".");
        textInfo = textInfo.replaceAll(COLON_REGEX, ":");
        return textInfo;
    }
}
