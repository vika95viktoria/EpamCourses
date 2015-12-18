package bsu.by.textparser.composite;

import java.util.ArrayList;

/**
 * Created by Виктория on 15.12.2015.
 */
public class Punctuation implements TextPart {
    private String symbol;
    private TextType textType;

    public Punctuation(String symbol) {
        this.symbol = symbol;
        textType = TextType.SIGN;
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
        return symbol;
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

    public String getWord() {
        return symbol;
    }

    public void setWord(String symbol) {
        this.symbol = symbol;
    }
}
