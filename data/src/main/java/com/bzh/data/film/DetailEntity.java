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
    private String previewImageUrl;
    private String introduction;
    private String downloadUrl;
    private String adTitle;
    private String adImage;
    private String adUrl;
    private String adPackageName;
    private String adClassName;

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

    public String getPreviewImageUrl() {
        return previewImageUrl;
    }

    public void setPreviewImageUrl(String previewImageUrl) {
        this.previewImageUrl = previewImageUrl;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
    }

    public String getAdImage() {
        return adImage;
    }

    public void setAdImage(String adImage) {
        this.adImage = adImage;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public String getAdPackageName() {
        return adPackageName;
    }

    public void setAdPackageName(String adPackageName) {
        this.adPackageName = adPackageName;
    }

    public String getAdClassName() {
        return adClassName;
    }

    public void setAdClassName(String adClassName) {
        this.adClassName = adClassName;
    }
}
