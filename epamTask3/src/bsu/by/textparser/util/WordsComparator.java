package bsu.by.textparser.util;

import bsu.by.textparser.composite.TextPart;

import java.util.Comparator;

/**
 * Created by Виктория on 18.12.2015.
 */
public class WordsComparator implements Comparator<TextPart> {
    @Override
    public int compare(TextPart textPart1, TextPart textPart2) {
        return SentenceUtils.getNumberOfWords(textPart1) - SentenceUtils.getNumberOfWords(textPart2);
    }
}
