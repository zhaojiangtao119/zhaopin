package com.labelwall.activity.controller;

import com.labelwall.activity.service.IActivityAccountService;
import com.labelwall.activity.vo.ActivityAccountVo;
import com.labelwall.common.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018-02-22.
 */
@RestController
@RequestMapping("/activity/account")
public class ActivityAccountController {

    @Autowired
    private IActivityAccountService activityAccountService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseObject<ActivityAccountVo> getUserAccount(@PathVariable("userId") Integer userId) {
        return activityAccountService.getUserAccount(userId);
    }

}
