package com.labelwall.mall.entity;

import java.io.Serializable;

public class Province implements Serializable {
    private Integer id;

    private String name;

    public Province(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Province() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}