package com.labelwall.mall.controller;

import com.github.pagehelper.PageInfo;
import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.service.IUserFollowsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017-12-21.
 */
@RestController
@RequestMapping("/userfollows/")
public class UserFollowController {

    @Autowired
    private IUserFollowsService userFollowsService;

    /**
     * 判断用户是否关注了某个用户
     *
     * @param session
     * @param followsId
     * @return
     */
    @RequestMapping(value = "is_follows/{id}", method = RequestMethod.GET)
    public ResponseObject<Boolean> isFollows(HttpSession session,
                                             @PathVariable("id") Integer followsId) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        return userFollowsService.isFollows(userDto.getId(), followsId);
    }

    /**
     * 点击关注按钮之后执行的动作
     *
     * @param session
     * @param followsId
     * @return
     */
    @RequestMapping(value = "do_follows/{id}", method = RequestMethod.POST)
    public ResponseObject doFollows(HttpSession session,
                                    @PathVariable("id") Integer followsId) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        return userFollowsService.doFollows(userDto.getId(), followsId);
    }

    /**
     * 获取当前用户关注的用户列表
     *
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "get_follows/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public ResponseObject<PageInfo> getFollows(HttpSession session,
                                               @PathVariable("pageNum") Integer pageNum,
                                               @PathVariable("pageSize") Integer pageSize) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        return userFollowsService.getFollows(userDto.getId(), pageNum, pageSize);
    }


}
