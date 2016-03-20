package com.epam.lowcost.tag;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.TagSupport;


import java.io.IOException;
@SuppressWarnings("serial")
public class DateForCabinetTag extends TagSupport {
    private Date date;
    private String locale;
    public void setDate(Date date) {
        this.date = date;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
    public SimpleDateFormat formateDateForCabinet( String locale) {
        Locale locale1 = new Locale(locale.substring(0, 2), locale.substring(3, 5));
        Locale.setDefault(locale1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm MMM d");
        return dateFormat;
    }


    private  String format(Date date, SimpleDateFormat dateFormat) {
        String formDate = dateFormat.format(date);
        return formDate;
    }
    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().write(format(date, formateDateForCabinet(locale)));
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

}
