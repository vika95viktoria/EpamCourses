
package entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "abstractTariff"
})
@XmlRootElement(name = "Tariffs", namespace = "http://www.example.com/tariffs")
public class Tariffs {

    @XmlElementRef(name = "abstractTariff", namespace = "http://www.example.com/tariffs", type = JAXBElement.class)
    protected List<JAXBElement<? extends AbstractTariff>> abstractTariff;


    public List<JAXBElement<? extends AbstractTariff>> getAbstractTariff() {
        if (abstractTariff == null) {
            abstractTariff = new ArrayList<JAXBElement<? extends AbstractTariff>>();
        }
        return this.abstractTariff;
    }

}
