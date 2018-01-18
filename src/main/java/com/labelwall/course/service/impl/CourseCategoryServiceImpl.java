package com.labelwall.course.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.course.dao.CourseCategoryMapper;
import com.labelwall.course.dto.CourseCategoryDto;
import com.labelwall.course.entity.CourseCategory;
import com.labelwall.course.service.ICourseCategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017-12-12.
 */
@Service("courseCategoryService")
public class CourseCategoryServiceImpl implements ICourseCategoryService {

    @Autowired
    private CourseCategoryMapper courseCategoryMapper;

    @Override
    public ResponseObject<List<CourseCategoryDto>> getCourseCategory(String code) {
        Map<String, CourseCategoryDto> courseCategoryDtoMap = Maps.newHashMap();
        List<CourseCategoryDto> courseCategoryDtoList = Lists.newArrayList();
        List<CourseCategory> courseCategoryList = courseCategoryMapper.getCourseCategory(code);
        if (CollectionUtils.isEmpty(courseCategoryList)) {
            return ResponseObject.fail(ResponseStatus.FAIL.getCode(),ResponseStatus.FAIL.getValue());
        }
        for (CourseCategory courseCategory : courseCategoryList) {
            if (courseCategory.getParentCode().equals("0")) {
                CourseCategoryDto dto = new CourseCategoryDto();
                BeanUtils.copyProperties(courseCategory, dto);
                courseCategoryDtoMap.put(courseCategory.getCode(), dto);
            } else {
                courseCategoryDtoMap.get(courseCategory.getParentCode()).getSubCourseCategory().add(courseCategory);
            }
        }
        for(Map.Entry<String,CourseCategoryDto> entry : courseCategoryDtoMap.entrySet()){
            courseCategoryDtoList.add(entry.getValue());
        }
        return ResponseObject.successStautsData(courseCategoryDtoList);
    }

    @Override
    public ResponseObject<List<Integer>> getCourseCategoryId(String code) {
        Set<CourseCategory> courseCategorySet = Sets.newHashSet();
        findChildrenCategoryList(courseCategorySet,code);
        List<Integer> resultList = Lists.newArrayList();
        for(CourseCategory item : courseCategorySet){
            resultList.add(item.getId());
        }
        return ResponseObject.successStautsData(resultList);
    }
    //递归查询分类的id(可以使所有的分类也可以是某一个分类下的所有分类的id)
    private Set<CourseCategory> findChildrenCategoryList(Set<CourseCategory> courseCategorySet, String code) {
        CourseCategory courseCategory = courseCategoryMapper.selectByCode(code);
        if(courseCategory != null){
            courseCategorySet.add(courseCategory);
        }
        List<CourseCategory> courseCategoryList = courseCategoryMapper.selectByParentCode(code);
        for(CourseCategory item : courseCategoryList){
            findChildrenCategoryList(courseCategorySet,item.getCode());
        }
        return courseCategorySet;
    }
}
