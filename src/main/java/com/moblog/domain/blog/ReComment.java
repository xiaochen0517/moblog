package com.moblog.domain.blog;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/2/23
 * @version 0.1
 */
public class ReComment {

    private int id;
    private String username;
    private String content;
    private String time;

    @Override
    public String toString() {
        return "ReComment{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
