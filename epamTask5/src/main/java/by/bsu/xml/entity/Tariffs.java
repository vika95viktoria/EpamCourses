package by.bsu.xml.entity;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "abstractTariffs"
})
@XmlRootElement(name = "Tariffs", namespace = "http://www.example.com/tariffs")
public class Tariffs {

    @XmlElementRef(name = "abstractTariffs", namespace = "http://www.example.com/tariffs", type = JAXBElement.class)
    protected List<AbstractTariff> abstractTariffs;


    public List<AbstractTariff> getAbstractTariffs() {
        if (abstractTariffs == null) {
            abstractTariffs = new ArrayList<AbstractTariff>();
        }
        return this.abstractTariffs;
    }

    public boolean add(AbstractTariff tariff) {
        if (abstractTariffs == null) {
            abstractTariffs = new ArrayList<AbstractTariff>();
        }
        return abstractTariffs.add(tariff);
    }

}
