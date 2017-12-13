package com.labelwall.course.service.impl;

import com.labelwall.common.CourseConst;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.course.dao.CourseCollectionsMapper;
import com.labelwall.course.entity.CourseCollections;
import com.labelwall.course.service.ICourseCollectionsService;
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

    @Override
    public ResponseObject<Boolean> isCollection(Integer userId, Integer courseId) {
        if (courseId == null) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
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
    public ResponseObject doCollection(Integer userId, Integer courseId) {
        if (courseId == null) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        CourseCollections courseCollections = new CourseCollections();
        courseCollections.setUserId(userId);
        courseCollections.setClassify(CourseConst.CollectionType.COLLECTION_CLASSIFY_COURSE.getCode());
        courseCollections.setObjectId(courseId);
        List<CourseCollections> courseCollectionsList = courseCollectionsMapper.selectByCollection(courseCollections);
        if (CollectionUtils.isEmpty(courseCollectionsList)) {
            //没有收藏该课程，点击进行收藏（添加记录）
            courseCollectionsMapper.insertSelective(courseCollections);
            return ResponseObject.successStatusMessage("收藏成功");
        } else {
            //收藏了该课程，点击取消收藏（删除记录）
            courseCollectionsMapper.deleteByPrimaryKey(courseCollectionsList.get(0).getId());
            return ResponseObject.successStatusMessage("收藏成功");
        }
    }
}
