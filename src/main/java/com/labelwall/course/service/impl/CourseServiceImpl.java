package com.labelwall.course.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.labelwall.common.CourseConst;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.course.dao.CourseMapper;
import com.labelwall.course.dto.CourseDto;
import com.labelwall.course.dto.CourseQueryDto;
import com.labelwall.course.entity.Course;
import com.labelwall.course.entity.Institution;
import com.labelwall.course.entity.InstitutionTeacher;
import com.labelwall.course.service.ICourseService;
import com.labelwall.course.service.IInstitutionTeacherService;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.service.IUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
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
    @Autowired
    private IInstitutionTeacherService institutionTeacherService;

    @Override
    public ResponseObject<List<Course>> getCommendCourse(Integer free) {
        List<Course> courseList = courseMapper.getCommendCourse(free);
        if (CollectionUtils.isEmpty(courseList)) {
            return ResponseObject.fail(ResponseStatus.FAIL.getCode(), ResponseStatus.FAIL.getValue());
        }
        return ResponseObject.successStautsData(courseList);
    }

    @Override
    public ResponseObject<PageInfo> getCourseList(CourseQueryDto courseQueryDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        String sortField = courseQueryDto.getSortField();
        if (StringUtils.isNotBlank(sortField)) {
            if (!sortField.equals(CourseConst.STUDY_COUNT) && !sortField.equals(CourseConst.UPDATE_TIME)) {
                return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                        ResponseStatus.ERROR_PARAM.getValue());
            }
        }
        List<Course> courseList = courseMapper.getCourseList(courseQueryDto);
        PageInfo pageInfo = new PageInfo(courseList);
        return ResponseObject.successStautsData(pageInfo);
    }

    @Override
    public ResponseObject<CourseDto> getCourse(Integer id) {
        if (id == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        Course course = courseMapper.selectByPrimaryKey(id);
        //加载讲师信息
        InstitutionTeacher institutionTeacher = institutionTeacherService.selectByPrimaryKey(course.getTeacherId());
        CourseDto courseDto = null;
        if(course != null){
            courseDto = new CourseDto();
            BeanUtils.copyProperties(course, courseDto);
        }
        courseDto.setInstitutionTeacher(institutionTeacher);
        return ResponseObject.successStautsData(courseDto);
    }

    @Override
    public Course selectByPrimaryKey(Integer id) {
        Course course = courseMapper.selectByPrimaryKey(id);
        return course;
    }

    @Override
    public List<Course> selectByCourseIds(List<Integer> courseIdList) {
        return courseMapper.selectByCourseIds(courseIdList);
    }

    @Override
    public ResponseObject<List<Course>> getInstitutionCourse(CourseQueryDto courseQueryDto) {
        List<Course> courseList = courseMapper.getInstitutionCourse(courseQueryDto);
        if (CollectionUtils.isEmpty(courseList)) {
            return ResponseObject.fail(ResponseStatus.FAIL.getCode(), ResponseStatus.FAIL.getValue());
        }
        return ResponseObject.successStautsData(courseList);
    }

    @Override
    public ResponseObject<List<Course>> getCoursesByTeacherId(Integer teacherId) {
        if(teacherId == null){
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),ResponseStatus.FAIL.getValue());
        }
        List<Course> courseList = courseMapper.getCourseByTeacherId(teacherId);
        if(CollectionUtils.isEmpty(courseList)){
            return ResponseObject.fail(ResponseStatus.FAIL.getCode(),ResponseStatus.FAIL.getValue());
        }
        return ResponseObject.successStautsData(courseList);
    }
}
