package com.labelwall.mall.service.impl;

import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.mall.dao.ProvinceMapper;
import com.labelwall.mall.entity.Province;
import com.labelwall.mall.service.IProvinceService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017-12-04.
 */
@Service("provinceService")
public class ProvinceServiceImpl implements IProvinceService {

    @Autowired
    private ProvinceMapper provinceMapper;

    @Override
    public ResponseObject<List<Province>> getProvinceList() {
        List<Province> provinceList = provinceMapper.getProvinceList();
        if (CollectionUtils.isEmpty(provinceList)) {
            return ResponseObject.fail(ResponseStatus.FAIL.getCode(),
                    ResponseStatus.FAIL.getValue());
        }
        return ResponseObject.successStautsData(provinceList);
    }

    @Override
    public Integer findIdByProvinceName(String name) {
        Integer provinceId = null;
        if (name != null) {
            provinceId = provinceMapper.findIdByProvinceName(name);
        }
        return provinceId;
    }


}
