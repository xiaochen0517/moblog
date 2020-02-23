package com.moblog.domain.blog;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/2/22
 * @version 0.1
 */
public class Recommend {

    int id;
    String title;

    @Override
    public String toString() {
        return "Recommend{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
