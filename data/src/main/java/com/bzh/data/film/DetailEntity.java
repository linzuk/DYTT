package com.bzh.data.film;

import java.util.Date;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-14<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class DetailEntity {

    private String viewkey;
    private String videoTitle;
    private String imageUrl;
    private String linkUrl;
    private String quality480p;
    private String videoDuration;
    private Boolean free;
    private Boolean isTicketOk; // 观影券是否可用
    private Date expireTime; // 观影券到期时间

    public String getViewkey() {
        return viewkey;
    }

    public void setViewkey(String viewkey) {
        this.viewkey = viewkey;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getQuality480p() {
        return quality480p;
    }

    public void setQuality480p(String quality480p) {
        this.quality480p = quality480p;
    }

    public String getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
    }

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

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
