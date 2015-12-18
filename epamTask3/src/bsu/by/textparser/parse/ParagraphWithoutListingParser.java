package bsu.by.textparser.parse;

import bsu.by.textparser.composite.Text;
import bsu.by.textparser.composite.TextType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Виктория on 15.12.2015.
 */
public class ParagraphWithoutListingParser {
    private static final String SENTENCE_REGEX = "[А-Я]([А-Яа-я\\s\\w\\p{Punct}&&[^.:]]+)([.:])";
    private static final String TEXT_WITHOUT_LISTING_REGEX = "([\\s\\w]){6,}[{=]([А-Яа-я\\s\\w\\p{Punct}&&[^\t]]+)([\\s\\S])";

    public static String getText(String text) {
        String s = text.replaceFirst(TEXT_WITHOUT_LISTING_REGEX, "");
        return s;
    }

    public static Text parse(String text) {
        Text finalText = new Text(TextType.PARAGRAPH_WITHOUT_LISTING);
        Pattern pattern = Pattern.compile(SENTENCE_REGEX);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String sentence = matcher.group();
            finalText.add(SentenceParser.parse(sentence));
        }
        return finalText;
    }
}
