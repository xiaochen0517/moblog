package com.moblog.domain;

import java.io.Serializable;
import java.sql.Date;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/1/26
 * @version 0.1
 */
public class Article implements Serializable {

    private int id;
    private int uid;
    private String title;
    private Date publisht;
    private Date reviset;
    private int browse;
    private int like;
    private int sortid;
    private String label;
    private String content;

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", publisht=" + publisht +
                ", reviset=" + reviset +
                ", browse=" + browse +
                ", like=" + like +
                ", sortid=" + sortid +
                ", label='" + label + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublisht() {
        return publisht;
    }

    public void setPublisht(Date publisht) {
        this.publisht = publisht;
    }

    public Date getReviset() {
        return reviset;
    }

    public void setReviset(Date reviset) {
        this.reviset = reviset;
    }

    public int getBrowse() {
        return browse;
    }

    public void setBrowse(int browse) {
        this.browse = browse;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getSortid() {
        return sortid;
    }

    public void setSortid(int sortid) {
        this.sortid = sortid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
