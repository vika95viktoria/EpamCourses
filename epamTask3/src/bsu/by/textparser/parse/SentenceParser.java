package bsu.by.textparser.parse;

import bsu.by.textparser.composite.Punctuation;
import bsu.by.textparser.composite.Text;
import bsu.by.textparser.composite.TextType;
import bsu.by.textparser.composite.Word;

/**
 * Created by Виктория on 15.12.2015.
 */
public class SentenceParser {
    public static Text parse(String sentence) {
        sentence = sentence.replaceAll("([-]\\s{2})", "");
        Text text = new Text(TextType.SENTENCE);
        Punctuation punctuation;
        Word word;
        while (!sentence.isEmpty()) {
            word = WordParser.parse(sentence);
            if (word != null) {
                text.add(word);
                sentence = sentence.replaceFirst(WordParser.getWordsRegex(), "");
            } else {
                punctuation = PunctuationParser.parse(sentence);
                sentence = sentence.replaceFirst(PunctuationParser.getPunctuationRegex(), "");
                text.add(punctuation);
            }

        }
        return text;
    }
}
