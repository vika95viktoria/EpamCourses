package bsu.by.textparser.composite;

import java.util.ArrayList;

/**
 * Created by Виктория on 14.12.2015.
 */
public interface TextPart {
    boolean add(TextPart textPart);

    boolean removeElement(TextPart textPart);

    TextPart getElement(int index);

    int size();

    TextType getTextType();

    ArrayList<TextPart> getTextParts();
}
