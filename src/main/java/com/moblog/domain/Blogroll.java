package com.moblog.domain;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/2/25
 * @version 0.1
 */
public class Blogroll {

    int id;
    String name;
    String link;

    @Override
    public String toString() {
        return "Blogroll{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
