package com.labelwall.activity.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.labelwall.activity.dao.ActivityCommentMapper;
import com.labelwall.activity.dto.ActivityCommentDto;
import com.labelwall.activity.service.IActivityCommentService;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018-02-05.
 */
@Service("activityCommentService")
public class ActivityCommentServiceImpl implements IActivityCommentService {

    @Autowired
    private ActivityCommentMapper activityCommentMapper;

    @Override
    public ResponseObject<PageInfo> getCommentList(Integer activityId, Integer pageNum, Integer pageSize) {
        if (activityId == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(), ResponseStatus.ERROR_PARAM.getValue());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<ActivityCommentDto> activityCommentDtoList = activityCommentMapper.getCommentMapper(activityId);
        PageInfo pageInfo = new PageInfo(activityCommentDtoList);
        return ResponseObject.successStautsData(pageInfo);
    }
}
