
package by.bsu.xml.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Tariff", namespace = "http://www.example.com/tariffs", propOrder = {
    "sms",
    "parameters"
})
public class Tariff
    extends AbstractTariff
{

    @XmlElement(namespace = "http://www.example.com/tariffs")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected int sms;
    @XmlElement(namespace = "http://www.example.com/tariffs", required = true)
    protected Parameters parameters;
    @XmlAttribute(name = "name", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    protected String name;
    @XmlAttribute(name = "operatorName")
    protected String operatorName;


    public int getSms() {
        return sms;
    }


    public void setSms(int value) {
        this.sms = value;
    }


    public Parameters getParameters() {
        if(parameters==null){
            parameters = new Parameters();
        }
        return parameters;
    }


    public void setParameters(Parameters value) {
        this.parameters = value;
    }


    public String getName() {
        return name;
    }


    public void setName(String value) {
        this.name = value;
    }


    public String getOperatorName() {
        return operatorName;
    }


    public void setOperatorName(String value) {
        this.operatorName = value;
    }

    @Override
    public String toString() {
        return "name=" + name + ", operatorName=" + operatorName +", payroll="+payroll +" "+callPrices+", sms=" + sms +
                " " + parameters;
    }
}
