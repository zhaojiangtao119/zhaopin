package com.labelwall.mall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.mall.dao.ProductCategoryMapper;
import com.labelwall.mall.dto.ProductCategoryDto;
import com.labelwall.mall.entity.ProductCategory;
import com.labelwall.mall.service.IProductCategoryService;
import com.labelwall.util.DateTimeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2017-12-04.
 */
@Service("productCategoryService")
public class ProductCategoryServiceImpl implements IProductCategoryService {

    @Autowired
    private ProductCategoryMapper categoryMapper;

    @Override
    public ResponseObject<List<ProductCategoryDto>> getCategoryList(Integer categoryId) {
        List<ProductCategory> categoryList = categoryMapper.getCategory(categoryId);
        if (CollectionUtils.isEmpty(categoryList)) {
            return ResponseObject.fail(ResponseStatus.FAIL.getCode(), ResponseStatus.FAIL.getValue());
        }
        List<ProductCategoryDto> categoryDtoList = new ArrayList<>();
        Map<Integer, ProductCategoryDto> categoryMap = new HashMap<>();
        for (ProductCategory category : categoryList) {
            ProductCategoryDto categoryDto = new ProductCategoryDto();
            BeanUtils.copyProperties(category, categoryDto);
            categoryDto.setCreateTimeStr(DateTimeUtil.dateToStr(category.getCreateTime()));
            categoryDto.setUpdateTimeStr(DateTimeUtil.dateToStr(category.getUpdateTime()));
            categoryDto.setCreateTime(null);
            categoryDto.setUpdateTime(null);
            if (category.getParentId() == 0) {//一级目录
                categoryMap.put(category.getId(), categoryDto);
            } else {
                categoryMap.get(category.getParentId()).getSubCategoryList().add(categoryDto);
            }
        }
        for (Map.Entry<Integer, ProductCategoryDto> entry : categoryMap.entrySet()) {
            categoryDtoList.add(entry.getValue());
        }
        return ResponseObject.successStautsData(categoryDtoList);
    }


    @Override
    public ResponseObject<List<Integer>> getCategoryAndChildrenByCategoryId(Integer categoryId) {
        Set<ProductCategory> categorySet = Sets.newHashSet();
        findChildrenCategoryList(categorySet, categoryId);
        List<Integer> categoryIdList = Lists.newArrayList();
        if (categoryId != null) {
            for (ProductCategory item : categorySet) {
                categoryIdList.add(item.getId());
            }
        }
        return ResponseObject.successStautsData(categoryIdList);
    }

    @Override
    public List<Integer> getCategoryAndChildrenIdByCategoryId(Integer categoryId) {
        Set<ProductCategory> categorySet = Sets.newHashSet();
        findChildrenCategoryList(categorySet, categoryId);
        List<Integer> categoryIdList = Lists.newArrayList();
        if (categoryId != null) {
            for (ProductCategory item : categorySet) {
                categoryIdList.add(item.getId());
            }
        }
        return categoryIdList;
    }

    @Override
    public ResponseObject<List<ProductCategory>> getAllCategory() {
        List<ProductCategory> productCategoryList = categoryMapper.getAllCategory();
        return ResponseObject.successStautsData(productCategoryList);
    }

    //获取当前品类和其子品类(递归获取)的id
    private Set<ProductCategory> findChildrenCategoryList(Set<ProductCategory> categorySet, Integer categoryId) {
        ProductCategory category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null) {
            categorySet.add(category);
        }
        List<ProductCategory> categoryList = categoryMapper.getCategoryChildrenByParentId(categoryId);
        for (ProductCategory item : categoryList) {
            findChildrenCategoryList(categorySet, item.getId());
        }
        return categorySet;
    }


}
