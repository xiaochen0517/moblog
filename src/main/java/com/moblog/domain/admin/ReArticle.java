package com.moblog.domain.admin;

import java.io.Serializable;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/2/2
 * @version 0.1
 */
public class ReArticle implements Serializable {

    private int id;
    private String nickname;
    private String title;
    private String publisht;
    private String reviset;
    private int browse;
    private int like;
    private String sortname;
    private String label;
    private String content;

    @Override
    public String toString() {
        return "ReArticle{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", title='" + title + '\'' +
                ", publisht='" + publisht + '\'' +
                ", reviset='" + reviset + '\'' +
                ", browse=" + browse +
                ", like=" + like +
                ", sortname='" + sortname + '\'' +
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisht() {
        return publisht;
    }

    public void setPublisht(String publisht) {
        this.publisht = publisht;
    }

    public String getReviset() {
        return reviset;
    }

    public void setReviset(String reviset) {
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

    public String getSortname() {
        return sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
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
