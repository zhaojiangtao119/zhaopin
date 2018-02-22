package com.labelwall.activity.dao;

import com.labelwall.activity.entity.ActivityAccount;

/**
 * Created by Administrator on 2018-02-22.
 */
public interface ActivityAccountMapper {

    ActivityAccount getUserAccount(Integer userId);
}
