package bsu.by.textparser.parse;

import bsu.by.textparser.composite.Text;
import bsu.by.textparser.composite.TextType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Виктория on 15.12.2015.
 */
public class TextParser {

    private static final String PARAGRAPH_REGEX = "\t([А-Яа-я\\s\\w\\p{Punct}&&[^\t]]+)";

    public static Text parse(String text) {
        Text finalText = new Text(TextType.TEXT);
        String textInfo = text.replaceAll("(\\p{Blank}{4})", "\t");
        Pattern pattern = Pattern.compile(PARAGRAPH_REGEX);
        Matcher matcher = pattern.matcher(textInfo);
        while (matcher.find()) {
            String paragraph = matcher.group();
            finalText.add(ParagraphParser.parse(paragraph));
        }
        return finalText;
    }
}
