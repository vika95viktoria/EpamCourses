package bsu.by.textparser.parse;

import bsu.by.textparser.composite.Listing;
import bsu.by.textparser.composite.Text;
import bsu.by.textparser.composite.TextType;

/**
 * Created by Виктория on 16.12.2015.
 */

public class ParagraphParser {
    public static Text parse(String paragraph) {
        Text text = new Text(TextType.PARAGRAPH);
        Listing listing;
        while (!paragraph.isEmpty()) {
            listing = ParagraphToListingParser.parse(paragraph);
            if (listing != null) {
                text.add(listing);
                paragraph = paragraph.replace(listing.getListing(), "");
            } else {
                String notListing = ParagraphWithoutListingParser.getText(paragraph);
                paragraph = paragraph.replace(notListing, "");
                Text textInfo = ParagraphWithoutListingParser.parse(notListing);
                text.add(textInfo);
            }

        }
        return text;
    }
}

