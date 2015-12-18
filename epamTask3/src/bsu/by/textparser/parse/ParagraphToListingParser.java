package bsu.by.textparser.parse;

import bsu.by.textparser.composite.Listing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Виктория on 15.12.2015.
 */
public class ParagraphToListingParser {

    private static final String LISTING_REGEX = "^([\\s\\w]){6,}[{=]([А-Яа-я\\s\\w\\p{Punct}&&[^\t]]+)";

    public static Listing parse(String text) {
        Pattern pattern = Pattern.compile(LISTING_REGEX);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return new Listing(matcher.group());
        } else
            return null;
    }
}
