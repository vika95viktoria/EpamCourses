package bsu.by.textparser.composite;

import java.util.ArrayList;

/**
 * Created by Виктория on 14.12.2015.
 */
public class Word implements TextPart {
    private String word;
    private TextType textType;

    public Word(String word) {
        this.word = word;
        textType = TextType.WORD;
    }

    @Override
    public boolean add(TextPart textPart) {
        return false;
    }

    @Override
    public boolean removeElement(TextPart textPart) {
        return false;
    }

    @Override
    public TextPart getElement(int index) {
        return this;
    }

    @Override
    public String toString() {
        return word+" ";
    }

    @Override
    public int size() {
        return 1;
    }

    public TextType getTextType() {
        return textType;
    }

    public ArrayList<TextPart> getTextParts() {
        return new ArrayList<TextPart>();
    }
}
