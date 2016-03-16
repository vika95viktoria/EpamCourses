package com.epam.lowcost.domain;

/**
 * Created by Виктория on 01.03.2016.
 */
public enum ServiceMessage {
    NOTICKET {
        {
            this.value = "Sorry, there is no such tickets anymore on this flight";
        }
    },
    CITYOK {
        {
            this.value = "City added successfully";
        }
    },
    ERRORPASSWORD {
        {
            this.value = "Incorrect password";
        }
    },
    USERNAMEPERSIST {
        {
            this.value = "Choose another username,please";
        }
    },
    CITYFAIL {
        {
            this.value = "Failed to add this city";
        }
    },
    NOMONEY {
        {
            this.value = "Sorry, you haven't enought money to buy this ticket";
        }
    },
    OKEDIT {
        {
            this.value = "All changes accepted";
        }
    },
    OKADD {
        {
            this.value = "Flights sucessfully added";
        }
    },
    OKBUY {
        {
            this.value = "You successfully buy the ticket";
        }
    },
    FAIL {
        {
            this.value = "Operation cancelled";
        }
    },
    OK {
        {
            this.value = "Operation success";
        }
    };
    String value;

    public String getValue() {
        return this.value;
    }
}
