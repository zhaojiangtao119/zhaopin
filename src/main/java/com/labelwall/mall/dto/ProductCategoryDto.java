package com.labelwall.mall.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.labelwall.mall.entity.ProductCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-12-04.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductCategoryDto extends ProductCategory {

    private String createTimeStr;

    private String updateTimeStr;

    private List<ProductCategoryDto> subCategoryList = new ArrayList<>();


    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public List<ProductCategoryDto> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<ProductCategoryDto> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }
}
