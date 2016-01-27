package by.bsu.xml.entity;

import javax.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractTariff", namespace = "http://www.example.com/tariffs", propOrder = {
        "payroll",
        "callPrices"
})
@XmlSeeAlso({
        Tariff.class
})
public class AbstractTariff {

    @XmlElement(namespace = "http://www.example.com/tariffs")
    protected int payroll;
    @XmlElement(namespace = "http://www.example.com/tariffs", required = true)
    protected CallPrices callPrices;


    public int getPayroll() {
        return payroll;
    }


    public void setPayroll(int value) {
        this.payroll = value;
    }


    public CallPrices getCallPrices() {
        if (callPrices == null) {
            callPrices = new CallPrices();
        }
        return callPrices;
    }


    public void setCallPrices(CallPrices value) {
        this.callPrices = value;
    }

    @Override
    public String toString() {
        return "payroll=" + payroll +
                ", callPrices=" + callPrices;
    }
}
