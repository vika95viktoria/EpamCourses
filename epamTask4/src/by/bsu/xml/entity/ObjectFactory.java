package by.bsu.xml.entity;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


@XmlRegistry
public class ObjectFactory {

    private final static QName _AbstractTariff_QNAME = new QName("http://www.example.com/tariffs", "abstractTariffs");
    private final static QName _Tariff_QNAME = new QName("http://www.example.com/tariffs", "tariff");


    public ObjectFactory() {
    }


    public Tariffs createTariffs() {
        return new Tariffs();
    }


    public AbstractTariff createAbstractTariff() {
        return new AbstractTariff();
    }


    public Tariff createTariff() {
        return new Tariff();
    }

    public CallPrices createCallPrices() {
        return new CallPrices();
    }


    public Parameters createParameters() {
        return new Parameters();
    }


    @XmlElementDecl(namespace = "http://www.example.com/tariffs", name = "abstractTariffs")
    public JAXBElement<AbstractTariff> createAbstractTariff(AbstractTariff value) {
        return new JAXBElement<AbstractTariff>(_AbstractTariff_QNAME, AbstractTariff.class, null, value);
    }


    @XmlElementDecl(namespace = "http://www.example.com/tariffs", name = "tariff", substitutionHeadNamespace = "http://www.example.com/tariffs", substitutionHeadName = "abstractTariffs")
    public JAXBElement<Tariff> createTariff(Tariff value) {
        return new JAXBElement<Tariff>(_Tariff_QNAME, Tariff.class, null, value);
    }

}
