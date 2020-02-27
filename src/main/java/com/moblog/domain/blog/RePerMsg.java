package com.moblog.domain.blog;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/2/25
 * @version 0.1
 */
public class RePerMsg {

    int status;
    String perphoto;
    String percontent;

    @Override
    public String toString() {
        return "RePerMsg{" +
                "status=" + status +
                ", perphoto='" + perphoto + '\'' +
                ", percontent='" + percontent + '\'' +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPerphoto() {
        return perphoto;
    }

    public void setPerphoto(String perphoto) {
        this.perphoto = perphoto;
    }

    public String getPercontent() {
        return percontent;
    }

    public void setPercontent(String percontent) {
        this.percontent = percontent;
    }
}
