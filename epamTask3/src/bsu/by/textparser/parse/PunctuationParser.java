package bsu.by.textparser.parse;

import bsu.by.textparser.composite.Punctuation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Виктория on 16.12.2015.
 */
public class PunctuationParser {
    private static final String PUNCTUATION_REGEX = "^(\\p{Punct}+)";

    public static Punctuation parse(String wordSign) {
        Pattern pattern = Pattern.compile(PUNCTUATION_REGEX);
        Matcher matcher = pattern.matcher(wordSign);
        if (matcher.find()) {
            return new Punctuation(matcher.group());
        } else
            return null;
    }

    public static String getPunctuationRegex() {
        return PUNCTUATION_REGEX;
    }
}
