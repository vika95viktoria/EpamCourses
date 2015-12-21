package bsu.by.textparser.composite;

import java.util.ArrayList;

/**
 * Created by Виктория on 14.12.2015.
 */
public class Listing implements TextPart {
    private String listing;
    private TextType textType;

    public Listing(String listing) {
        this.listing = listing;
        textType = TextType.LISTING;
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

    public String getListing() {
        return listing;
    }

    @Override
    public String toString() {
        return listing;
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
