package com.moblog.domain;

import java.io.Serializable;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/1/26
 * @version 0.1
 */
public class Sort implements Serializable {

    private int id;
    private String name;
    private boolean defsort;

    @Override
    public String toString() {
        return "Sort{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", defsort=" + defsort +
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

    public boolean isDefsort() {
        return defsort;
    }

    public void setDefsort(boolean defsort) {
        this.defsort = defsort;
    }
}
