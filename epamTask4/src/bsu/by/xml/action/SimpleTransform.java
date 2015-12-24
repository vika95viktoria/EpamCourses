package bsu.by.xml.action;

/**
 * Created by Виктория on 25.12.2015.
 */
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
public class SimpleTransform {
    public static void transform(String fileName) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer(new StreamSource("data/tariffs.xsl"));
            transformer.transform(new StreamSource("data/tariffs.xml"),
                    new StreamResult(fileName));
            System.out.println("Transform " + fileName + " complete");
        } catch(TransformerException e) {
            System.err.println("Impossible transform file " + fileName + " : " + e);
        }
    }
}