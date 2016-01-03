package by.bsu.xml.entity;

import javax.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "callPrices", namespace = "http://www.example.com/tariffs", propOrder = {
        "inNet",
        "outNet",
        "landline"
})
public class CallPrices {

    @XmlElement(namespace = "http://www.example.com/tariffs")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected int inNet;
    @XmlElement(namespace = "http://www.example.com/tariffs")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected int outNet;
    @XmlElement(namespace = "http://www.example.com/tariffs")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected int landline;


    public int getInNet() {
        return inNet;
    }


    public void setInNet(int value) {
        this.inNet = value;
    }


    public int getOutNet() {
        return outNet;
    }


    public void setOutNet(int value) {
        this.outNet = value;
    }


    public int getLandline() {
        return landline;
    }


    public void setLandline(int value) {
        this.landline = value;
    }

    @Override
    public String toString() {
        return "inNet=" + inNet +
                ", outNet=" + outNet +
                ", landline=" + landline;
    }
}
