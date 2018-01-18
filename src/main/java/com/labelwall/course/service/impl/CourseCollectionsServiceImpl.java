package com.labelwall.course.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.labelwall.common.CourseConst;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.course.dao.CourseCollectionsMapper;
import com.labelwall.course.entity.Course;
import com.labelwall.course.entity.CourseCollections;
import com.labelwall.course.service.ICourseCollectionsService;
import com.labelwall.course.service.ICourseService;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.entity.User;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017-12-13.
 */
@Service("courseCollectionsService")
public class CourseCollectionsServiceImpl implements ICourseCollectionsService {

    @Autowired
    private CourseCollectionsMapper courseCollectionsMapper;
    @Autowired
    private ICourseService courseService;


    @Override
    public ResponseObject<Boolean> isCollection(Integer userId, Integer courseId) {
        if (courseId == null || userId == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        CourseCollections courseCollections = new CourseCollections();
        courseCollections.setUserId(userId);
        courseCollections.setClassify(CourseConst.CollectionType.COLLECTION_CLASSIFY_COURSE.getCode());
        courseCollections.setObjectId(courseId);
        List<CourseCollections> courseCollectionsList = courseCollectionsMapper.selectByCollection(courseCollections);
        if (CollectionUtils.isEmpty(courseCollectionsList)) {
            //未收藏
            return ResponseObject.successStautsData(false);
        }
        //已收藏
        return ResponseObject.successStautsData(true);
    }

    @Override
    public ResponseObject doCollection(UserDto userDto, Integer courseId) {
        Integer userId = userDto.getId();
        if (courseId == null || userId == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        CourseCollections courseCollections = new CourseCollections();
        courseCollections.setUserId(userId);
        courseCollections.setClassify(CourseConst.CollectionType.COLLECTION_CLASSIFY_COURSE.getCode());
        courseCollections.setObjectId(courseId);
        List<CourseCollections> courseCollectionsList = courseCollectionsMapper.selectByCollection(courseCollections);
        if (CollectionUtils.isEmpty(courseCollectionsList)) {
            //没有收藏该课程，点击进行收藏（添加记录）
            courseCollections.setCreateUser(userDto.getUsername());
            courseCollections.setUpdateUser(userDto.getUsername());
            courseCollectionsMapper.insertSelective(courseCollections);
            return ResponseObject.successStatus();
        } else {
            //收藏了该课程，点击取消收藏（删除记录）
            courseCollectionsMapper.deleteByPrimaryKey(courseCollectionsList.get(0).getId());
            return ResponseObject.successStatus();
        }
    }

    @Override
    public ResponseObject<PageInfo> getCourse(Integer userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        //获取关注课程的id集合
        List<CourseCollections> courseCollectionsList = courseCollectionsMapper.selectByUserId(userId);
        List<Integer> courseIdList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(courseCollectionsList)) {
            return ResponseObject.successStautsData(null);
        }
        for (CourseCollections item : courseCollectionsList) {
            courseIdList.add(item.getObjectId());
        }
        //通过courseIdList获取关注的课程集合
        List<Course> courseList = courseService.selectByCourseIds(courseIdList);
        PageInfo pageInfo = new PageInfo(courseList);
        return ResponseObject.successStautsData(pageInfo);
    }
}
