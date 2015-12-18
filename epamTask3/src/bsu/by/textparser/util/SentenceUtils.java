package bsu.by.textparser.util;

import bsu.by.textparser.composite.TextPart;
import bsu.by.textparser.composite.TextType;

import java.util.ArrayList;

/**
 * Created by Виктория on 18.12.2015.
 */
public class SentenceUtils {
    public static ArrayList<TextPart> getSentences(TextPart text) {
        ArrayList<TextPart> finalInfo = new ArrayList<>();
        for (int i = 0; i < text.size(); i++) {
            if (text.getElement(i).getTextType().equals(TextType.SENTENCE)) {
                for (int j = 0; j < text.size(); j++) {
                    finalInfo.add(text.getElement(j));
                }
                return finalInfo;
            }
            if (text.getElement(i).getTextType().equals(TextType.LISTING)) {
                continue;
            }
            finalInfo.addAll(getSentences(text.getElement(i)));
        }
        return finalInfo;
    }

    public static int getNumberOfWords(TextPart textPart) {
        int count = 0;
        for (int j = 0; j < textPart.size(); j++) {
            if (textPart.getElement(j).getTextType().equals(TextType.WORD)) {
                count++;
            }
        }
        return count;
    }
}
