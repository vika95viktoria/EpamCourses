package bsu.by.textparser.action;

import bsu.by.textparser.composite.Text;
import bsu.by.textparser.composite.TextPart;
import bsu.by.textparser.util.SentenceUtils;
import bsu.by.textparser.util.WordsComparator;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Виктория on 18.12.2015.
 */
public class SentencesSorting {
    public static ArrayList<TextPart> sortSentencesByNumberOfWords(Text textParts) {
        ArrayList<TextPart> sentences = SentenceUtils.getSentences(textParts);
        Collections.sort(sentences, new WordsComparator());
        return sentences;
    }
}
