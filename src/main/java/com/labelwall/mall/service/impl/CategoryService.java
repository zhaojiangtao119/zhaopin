package com.labelwall.mall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.dao.CategoryMapper;
import com.labelwall.mall.dto.CategoryDto;
import com.labelwall.mall.entity.Category;
import com.labelwall.mall.service.ICategoryService;
import com.labelwall.util.DateTimeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2017-12-04.
 */
@Service("categoryService")
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseObject<List<CategoryDto>> getCategoryList(Integer categoryId) {
        List<Category> categoryList = categoryMapper.getCategory(categoryId);
        if (CollectionUtils.isEmpty(categoryList)) {
            return ResponseObject.failStatusMessage("获取失败");
        }
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        Map<Integer, CategoryDto> categoryMap = new HashMap<>();
        for (Category category : categoryList) {
            CategoryDto categoryDto = new CategoryDto();
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
        for (Map.Entry<Integer, CategoryDto> entry : categoryMap.entrySet()) {
            CategoryDto categoryDto = entry.getValue();
            categoryDtoList.add(categoryDto);
        }
        return ResponseObject.successStautsData(categoryDtoList);
    }

    //获取当前品类和其子品类(递归获取)的id
    @Override
    public ResponseObject<List<Integer>> getCategoryAndChildrenByCategoryId(Integer categoryId){
        Set<Category> categorySet = Sets.newHashSet();
        findChildrenCategoryList(categorySet,categoryId);
        List<Integer> categoryIdList = Lists.newArrayList();
        if(categoryId != null){
            for(Category item : categorySet){
                categoryIdList.add(item.getId());
            }
        }
        return ResponseObject.successStautsData(categoryIdList);
    }

    private Set<Category> findChildrenCategoryList(Set<Category> categorySet, Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category != null){
            categorySet.add(category);
        }
        List<Category> categoryList = categoryMapper.getCategoryChildrenByParentId(categoryId);
        for(Category item : categoryList){
            findChildrenCategoryList(categorySet,item.getId());
        }
        return categorySet;
    }


}
