package bsu.by.textparser.main;

import bsu.by.textparser.action.SentencesSorting;
import bsu.by.textparser.action.WordsSwaper;
import bsu.by.textparser.composite.Text;
import bsu.by.textparser.composite.TextPart;
import bsu.by.textparser.parse.TextParser;
import bsu.by.textparser.service.TextPrinter;
import bsu.by.textparser.service.TextReader;
import org.apache.log4j.PropertyConfigurator;

import java.util.ArrayList;

/**
 * Created by Виктория on 14.12.2015.
 */
public class Main {
    static {
        PropertyConfigurator.configure("resources/log4j.properties");
    }

    public static void main(String[] args) {
        String info = TextReader.readText("resources/input.txt");
        Text textParts = TextParser.parse(info);
        TextPrinter.printText(textParts);
        ArrayList<TextPart> sort = SentencesSorting.sortSentencesByNumberOfWords(textParts);
        TextPrinter.printSentences(sort);
        WordsSwaper.swap(textParts);
        TextPrinter.printText(textParts);
    }
}
