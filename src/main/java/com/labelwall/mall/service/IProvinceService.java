package com.labelwall.mall.service;

import com.labelwall.common.ResponseObject;
import com.labelwall.mall.entity.Province;

import java.util.List;

/**
 * Created by Administrator on 2017-12-04.
 */
public interface IProvinceService {
    ResponseObject<List<Province>> getProvinceList();

    Integer findIdByProvinceName(String name);
}
