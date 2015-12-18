package bsu.by.textparser.service;

import bsu.by.textparser.composite.Text;
import bsu.by.textparser.composite.TextPart;

import java.util.ArrayList;

/**
 * Created by Виктория on 16.12.2015.
 */
public class TextPrinter {
    public static void printText(Text text) {
        System.out.println(text);
    }

    public static void printSentences(ArrayList<TextPart> sentences) {
        System.out.println();
        System.out.println("SENTENCES SORTED BY NUMBER OF WORDS");
        for (TextPart sentence : sentences) {
            System.out.println(sentence);
            System.out.println();
        }
        System.out.println();
    }
}
