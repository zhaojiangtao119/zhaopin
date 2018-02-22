package com.labelwall.activity.vo;

import com.labelwall.activity.entity.ActivityAccountOrder;

/**
 * Created by Administrator on 2018-02-22.
 */
public class ActivityAccountOrderVo extends ActivityAccountOrder {

    private String statusDesc;

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
}
