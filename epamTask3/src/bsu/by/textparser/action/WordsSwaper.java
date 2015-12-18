package bsu.by.textparser.action;

import bsu.by.textparser.composite.TextPart;
import bsu.by.textparser.composite.TextType;

import java.util.Collections;

/**
 * Created by Виктория on 16.12.2015.
 */
public class WordsSwaper {
    public static void swap(TextPart text) {
        for (int i = 0; i < text.size(); i++) {
            if (text.getElement(i).getTextType().equals(TextType.SENTENCE)) {
                for (int j = 0; j < text.size(); j++) {
                    TextPart sentence = text.getElement(j);
                    if (sentence.size() > 2) {
                        formatWords(sentence, j);
                    }
                    Collections.swap(sentence.getTextParts(), 0, sentence.size() - 2);
                }
                return;
            }
            if (text.getElement(i).getTextType().equals(TextType.LISTING)) {
                continue;
            }
            swap(text.getElement(i));
        }
    }

    public static void formatWords(TextPart sentence, int j) {
        int length = sentence.getElement(0).getWord().length();
        sentence.getElement(0).setWord(sentence.getElement(0).getWord().substring(0, length - 1));
        sentence.getElement(sentence.size() - 2).setWord(sentence.getElement(sentence.size() - 2).getWord() + " ");
    }
}

