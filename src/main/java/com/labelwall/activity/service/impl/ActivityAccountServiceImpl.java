package com.labelwall.activity.service.impl;

import com.labelwall.activity.dao.ActivityAccountMapper;
import com.labelwall.activity.entity.ActivityAccount;
import com.labelwall.activity.service.IActivityAccountService;
import com.labelwall.activity.vo.ActivityAccountVo;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018-02-22.
 */
@Service("activityAccountService")
public class ActivityAccountServiceImpl implements IActivityAccountService {

    @Autowired
    private ActivityAccountMapper activityAccountMapper;

    @Override
    public ResponseObject<ActivityAccountVo> getUserAccount(Integer userId) {
        if (userId == null) {
            return ResponseObject.
                    fail(ResponseStatus.ERROR_PARAM.getCode(), ResponseStatus.ERROR_PARAM.getValue());
        }
        ActivityAccount activityAccount = activityAccountMapper.getUserAccount(userId);
        ActivityAccountVo activityAccountVo = new ActivityAccountVo();
        if (activityAccount != null) {
            BeanUtils.copyProperties(activityAccount, activityAccountVo);
        }
        return ResponseObject.successStautsData(activityAccountVo);
    }

    @Override
    public int updateAccountJindouNum(Integer accountId, Integer jindouCount) {
        return activityAccountMapper.updateAccountJindouNum(accountId, jindouCount);
    }
}
