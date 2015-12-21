package bsu.by.textparser.composite;

import bsu.by.textparser.service.TextFormatter;
import bsu.by.textparser.service.TextPrinter;

import java.util.ArrayList;

/**
 * Created by Виктория on 14.12.2015.
 */
public class Text implements TextPart {
    private ArrayList<TextPart> textParts = new ArrayList<>();
    private TextType textType;

    public Text(TextType textType) {
        this.textType = textType;
    }

    @Override
    public boolean add(TextPart textPart) {
        return textParts.add(textPart);
    }

    @Override
    public TextPart getElement(int index) {
        return textParts.get(index);
    }

    @Override
    public boolean removeElement(TextPart textPart) {
        return textParts.remove(textPart);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(TextPart textPart:textParts){
            sb.append(textPart);
        }
        return TextFormatter.formatText(sb.toString());
    }

    public TextType getTextType() {
        return textType;
    }

    @Override
    public int size() {
        return textParts.size();
    }

    public ArrayList<TextPart> getTextParts() {
        return textParts;
    }
}
