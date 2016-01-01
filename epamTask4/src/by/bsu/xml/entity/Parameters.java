package by.bsu.xml.entity;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "parameters", namespace = "http://www.example.com/tariffs", propOrder = {
        "favourites",
        "tarification",
        "payment"
})
public class Parameters {

    @XmlElement(namespace = "http://www.example.com/tariffs")
    protected int favourites;
    @XmlElement(namespace = "http://www.example.com/tariffs", required = true)
    protected String tarification;
    @XmlElement(namespace = "http://www.example.com/tariffs", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger payment;

    public int getFavourites() {
        return favourites;
    }


    public void setFavourites(int value) {
        this.favourites = value;
    }


    public String getTarification() {
        return tarification;
    }


    public void setTarification(String value) {
        this.tarification = value;
    }


    public BigInteger getPayment() {
        return payment;
    }


    public void setPayment(BigInteger value) {
        this.payment = value;
    }

}
