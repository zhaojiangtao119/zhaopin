package com.labelwall.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.mall.dao.UserFollowsMapper;
import com.labelwall.mall.entity.User;
import com.labelwall.mall.entity.UserFollows;
import com.labelwall.mall.service.IUserFollowsService;
import com.labelwall.mall.service.IUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-12-21.
 */
@Service("userFollowService")
public class UserFollowServiceImpl implements IUserFollowsService {

    @Autowired
    private UserFollowsMapper userFollowsMapper;
    @Autowired
    private IUserService userService;

    @Override
    public ResponseObject<Boolean> isFollows(Integer userId, Integer followsId) {
        if (userId == null || followsId == null) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        UserFollows userFollows = userFollowsMapper.selectByUserIdFollowsId(userId, followsId);
        if (userFollows == null) {//没有关注该用户
            return ResponseObject.successStautsData(false);
        }
        return ResponseObject.successStautsData(true);
    }

    @Override
    public ResponseObject doFollows(Integer userId, Integer followsId) {
        if (userId == null || followsId == null) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        UserFollows userFollows = userFollowsMapper.selectByUserIdFollowsId(userId, followsId);
        if (userFollows == null) {//没有关注该用户，进行关注
            userFollows.setUserId(userId);
            userFollows.setFollowId(followsId);
            userFollowsMapper.insertSelective(userFollows);
            return ResponseObject.successStatus();
        } else {
            userFollowsMapper.deleteByPrimaryKey(userFollows.getId());
            return ResponseObject.successStatus();
        }
    }

    @Override
    public ResponseObject<PageInfo> getFollows(Integer userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<UserFollows> userFollowsList = userFollowsMapper.selectByUserId(userId);
        if(CollectionUtils.isEmpty(userFollowsList)){
            return ResponseObject.successStautsData(null);
        }
        List<User> userList = new ArrayList<>();
        List<Integer> userIdList = new ArrayList<>();
        for(UserFollows item : userFollowsList){
            userIdList.add(item.getFollowId());
        }
        userList = userService.selectByUserIds(userIdList);
        PageInfo pageInfo = new PageInfo(userList);
        return ResponseObject.successStautsData(pageInfo);
    }
}
