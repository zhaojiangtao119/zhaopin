package com.labelwall.course.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.labelwall.common.CourseConst;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.course.dao.CourseMapper;
import com.labelwall.course.dto.CourseQueryDto;
import com.labelwall.course.entity.Course;
import com.labelwall.course.service.ICourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017-12-13.
 */
@Service("courseService")
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public ResponseObject<List<Course>> getCommendCourse(Integer free) {
        List<Course> courseList = courseMapper.getCommendCourse(free);
        return ResponseObject.successStautsData(courseList);
    }

    @Override
    public ResponseObject<PageInfo> getCourse(CourseQueryDto courseQueryDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        String sortField = courseQueryDto.getSortField();
        if (StringUtils.isNotBlank(sortField)) {
            if (!sortField.equals(CourseConst.STUDY_COUNT) && !sortField.equals(CourseConst.UPDATE_TIME)) {
                return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
            }
        }
        List<Course> courseList = courseMapper.getCourse(courseQueryDto);
        PageInfo pageInfo = new PageInfo(courseList);
        return ResponseObject.successStautsData(pageInfo);
    }
}
