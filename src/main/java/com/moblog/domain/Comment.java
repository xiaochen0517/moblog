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
public class Comment implements Serializable {

    private int id;
    private int aid;
    private int uid;
    private Date time;
    private String content;
    private boolean status;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", aid=" + aid +
                ", uid=" + uid +
                ", time=" + time +
                ", content='" + content + '\'' +
                ", status=" + status +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
