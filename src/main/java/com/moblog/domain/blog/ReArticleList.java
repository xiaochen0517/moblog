package com.moblog.domain.blog;

/**
 * 功能：
 * 文章返回数据
 * @author MoChen
 * Date  2020/2/19
 * @version 0.1
 */
public class ReArticleList {

    private int id;
    private String username;
    private String title;
    private String publisht;
    private int browse;
    private int like;
    private String sort;
    private String label;
    private boolean status;

    @Override
    public String toString() {
        return "ReArticleList{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", title='" + title + '\'' +
                ", publisht='" + publisht + '\'' +
                ", browse=" + browse +
                ", like=" + like +
                ", sort='" + sort + '\'' +
                ", label='" + label + '\'' +
                ", status=" + status +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
