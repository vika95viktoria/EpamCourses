package bsu.by.textparser.parse;

import bsu.by.textparser.composite.Word;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Виктория on 16.12.2015.
 */
public class WordParser {
    private static final String WORDS_REGEX = "^((\\s{1,})?)([А-Яа-я\\w]+)(\\s?)";

    public static Word parse(String wordSign) {
        Pattern pattern = Pattern.compile(WORDS_REGEX);
        Matcher matcher = pattern.matcher(wordSign);
        if (matcher.find()) {
            return new Word(matcher.group());
        } else
            return null;
    }

    public static String getWordsRegex() {
        return WORDS_REGEX;
    }
}
