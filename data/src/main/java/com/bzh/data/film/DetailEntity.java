package com.bzh.data.film;

import com.bzh.common.utils.AesKit;
import com.bzh.common.utils.UrlKit;

import org.jsoup.helper.StringUtil;

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

    private String id;
    private String title;
    private String content;
    private String image;
    private String cover;
    private String preview;
    private String video;
    private String type;
    private Boolean hot;
    private Boolean free;
    private Boolean show;
    private String publishDate;
    private Boolean isTicketOk; // 观影券是否可用
    private Date expireTime; // 观影券到期时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return UrlKit.decodeUrl(image);
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCover() {
        return UrlKit.decodeUrl(cover);
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPreview() {
        return UrlKit.decodeUrl(preview);
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getVideo() {
        return UrlKit.decodeUrl(video);
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getHot() {
        return hot;
    }

    public void setHot(Boolean hot) {
        this.hot = hot;
    }

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
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
