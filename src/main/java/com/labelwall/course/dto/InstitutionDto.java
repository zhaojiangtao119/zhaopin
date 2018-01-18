package com.labelwall.course.dto;

import com.labelwall.course.entity.Institution;

/**
 * Created by Administrator on 2017-12-29.
 */
public class InstitutionDto extends Institution {

    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
