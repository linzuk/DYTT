package com.bzh.data.ticket;

import java.util.Date;

/**
 * Created by linzuk on 2017/10/6.
 */

public class TicketValidateEntity {

    private Boolean isTicketOk;
    private Date expireTime;

    public Boolean getTicketOk() {
        return isTicketOk;
    }

    public void setTicketOk(Boolean ticketOk) {
        isTicketOk = ticketOk;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
