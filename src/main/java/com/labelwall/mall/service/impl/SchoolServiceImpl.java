package com.labelwall.mall.service.impl;

import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dao.SchoolMapper;
import com.labelwall.mall.entity.School;
import com.labelwall.mall.service.ISchoolService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017-12-04.
 */
@Service("schoolSchool")
public class SchoolServiceImpl implements ISchoolService {

    @Autowired
    private SchoolMapper schoolMapper;

    @Override
    public ResponseObject<List<School>> getSchoolByProvinceId(Integer provinceId) {
        List<School> schoolList = schoolMapper.getSchoolListByProvinceId(provinceId);
        if(CollectionUtils.isEmpty(schoolList)){
            return ResponseObject.failStatusMessage("获取失败");
        }
        return ResponseObject.successStautsData(schoolList);
    }
}
