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
}

