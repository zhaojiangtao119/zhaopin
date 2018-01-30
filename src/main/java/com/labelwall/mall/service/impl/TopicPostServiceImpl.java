package com.labelwall.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.mall.dao.TopicPostMapper;
import com.labelwall.mall.dto.TopicPostDto;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.entity.TopicPost;
import com.labelwall.mall.service.ITopicPostService;
import com.labelwall.mall.service.IUserService;
import com.labelwall.util.storage.QiniuStorage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Array;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017-12-05.
 */
@Service("topicPostService")
public class TopicPostServiceImpl implements ITopicPostService {

    private static final Logger logger = LoggerFactory.getLogger(TopicPostServiceImpl.class);

    @Autowired
    private TopicPostMapper topicPostMapper;
    @Autowired
    private IUserService userService;

    @Override
    public ResponseObject<PageInfo> getTopicPostList(TopicPostDto topicPostDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isBlank(topicPostDto.getKeyword())) {
            topicPostDto.setKeyword(null);
        }
        List<TopicPostDto> topicPostDtoList = topicPostMapper.getTopicPost(topicPostDto);
        for (TopicPostDto item : topicPostDtoList) {
           /* item.setCreateTimeStr(DateTimeUtil.dateToStr(item.getCreateTime()));
            item.setUpdateTimeStr(DateTimeUtil.dateToStr(item.getUpdateTime()));
            item.setCreateTime(null);
            item.setUpdateTime(null);*/
            //加载帖子图片
            if (item.getImage() != null) {
                //item.setImage(QiniuStorage.getUrl(item.getImage()));
                String[] images = item.getImage().split(",");
                if (images.length > 0) {
                    String url = QiniuStorage.getUrl(images[0]);//取第一张图片作为主图
                    item.setImage(url);
                }
            }
            //加载用户头像
            if (item.getUserDto().getHead() != null) {
                item.getUserDto().setHead(QiniuStorage.getUserHeadUrl(item.getUserDto().getHead()));
            } else {
                item.getUserDto().setHead(QiniuStorage.getUserHeadUrl(Const.DEFAULT_USER_HEAD));
            }
        }
        PageInfo pageInfo = new PageInfo(topicPostDtoList);
        return ResponseObject.successStautsData(pageInfo);
    }

    @Override
    public ResponseObject<TopicPostDto> addPublishPost(TopicPostDto topicPostDto) {
        //发表帖子
        //判断是否上传了图片
        if (topicPostDto.getMultipartFile() != null && topicPostDto.getMultipartFile().getSize() > 0) {
            try {
                byte[] postImage = topicPostDto.getMultipartFile().getBytes();
                String imageKey = QiniuStorage.uploadPostImage(postImage);
                topicPostDto.setImage(imageKey);
            } catch (IOException e) {
                logger.error("帖子图片解析失败", e);
                return ResponseObject.failStatusMessage("发表失败");
            }
        }

        TopicPost topicPost = new TopicPost();
        BeanUtils.copyProperties(topicPostDto, topicPost);
        topicPost = assembleTopicPost(topicPost);
        int rowCount = topicPostMapper.insertSelective(topicPost);
        if (rowCount > 0) {
            //新增成功，获取新增的数据
            TopicPostDto topicPostDtoNew = topicPostMapper.selectByPrimaryKey(topicPost.getId());
            /*topicPostDtoNew.setCreateTimeStr(DateTimeUtil.dateToStr(topicPostDtoNew.getCreateTime()));
            topicPostDtoNew.setUpdateTimeStr(DateTimeUtil.dateToStr(topicPostDtoNew.getUpdateTime()));
            topicPostDtoNew.setCreateTime(null);
            topicPostDtoNew.setUpdateTime(null);*/
            //获取图片
            if (topicPostDtoNew.getImage() != null) {
                topicPostDtoNew.setImage(QiniuStorage.getUrl(topicPostDtoNew.getImage()));
            }
            //加载用户头像
            if (topicPostDtoNew.getUserDto().getHead() != null) {
                topicPostDtoNew.getUserDto().setHead(QiniuStorage.getUserHeadUrl(topicPostDtoNew.getUserDto().getHead()));
            } else {
                topicPostDtoNew.getUserDto().setHead(QiniuStorage.getUserHeadUrl(Const.DEFAULT_USER_HEAD));
            }
            return ResponseObject.successStautsData(topicPostDtoNew);
        }
        return ResponseObject.fail(ResponseStatus.FAIL.getCode(),
                ResponseStatus.FAIL.getValue());
    }

    private TopicPost assembleTopicPost(TopicPost topicPost) {
        //组装schoolId，provinceId,cityId,countyId,默认使用用户的基本信息，
        UserDto userDto = new UserDto();
        ResponseObject<UserDto> response = userService.selectByUserId(topicPost.getUserId());
        if (response.isSuccess()) {
            userDto = response.getData();
        }
        if (topicPost.getSchoolId() == null) {
            if (userDto.getSchoolId() != null) {
                topicPost.setSchoolId(userDto.getSchoolId());
            }
        }
        if (topicPost.getProvinceId() == null) {
            if (userDto.getProvinceId() != null) {
                topicPost.setProvinceId(userDto.getProvinceId());
            }
        }
        if (topicPost.getCityId() == null) {
            if (userDto.getCityId() != null) {
                topicPost.setCityId(userDto.getCityId());
            }
        }
        if (topicPost.getCountyId() == null) {
            if (userDto.getCountyId() != null) {
                topicPost.setCountyId(userDto.getCountyId());
            }
        }
        return topicPost;
    }

    @Override
    public ResponseObject updatePostLikeDislike(Integer topicPostId, Integer type) {
        if (topicPostId == null || type == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        int rowCount;
        if (type == Const.PostClickType.LIKE_CLICK) {
            rowCount = topicPostMapper.updatePostLike(topicPostId);
        } else if (type == Const.PostClickType.DISLIKE_CLICK) {
            rowCount = topicPostMapper.updatePostDislike(topicPostId);
        } else {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        if (rowCount > 0) {
            return ResponseObject.successStatus();
        }
        return ResponseObject.fail(ResponseStatus.FAIL.getCode(),
                ResponseStatus.FAIL.getValue());
    }

    @Override
    public ResponseObject<TopicPostDto> getTopicPostById(Integer topicPostId) {
        if (topicPostId == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        TopicPostDto topicPostDto = topicPostMapper.selectByPrimaryKey(topicPostId);
        if (topicPostDto != null) {
            // TODO 加载帖子图片
            if (topicPostDto.getImage() != null) {
                topicPostDto.setImage(QiniuStorage.getUrl(topicPostDto.getImage()));
                String[] images = topicPostDto.getImage().split(",");
                if (images.length > 0) {
                    String imageUrl = QiniuStorage.getUrl(images[0]);
                    topicPostDto.setImage(imageUrl);
                }
            }
            //加载用户头像
            if (topicPostDto.getUserDto().getHead() != null) {
                topicPostDto.getUserDto().setHead(QiniuStorage.getUserHeadUrl(topicPostDto.getUserDto().getHead()));
            } else {
                topicPostDto.getUserDto().setHead(QiniuStorage.getUserHeadUrl(Const.DEFAULT_USER_HEAD));
            }
            return ResponseObject.successStautsData(topicPostDto);
        }
        return ResponseObject.fail(ResponseStatus.FAIL.getCode(),
                ResponseStatus.FAIL.getValue());
    }

    @Override
    public int updatePostReplyNum(Integer topicPostId) {
        return topicPostMapper.updatePostRelpyNum(topicPostId);
    }
}
