package com.labelwall.mall.service.impl;

import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.mall.dao.SchoolMapper;
import com.labelwall.mall.entity.Province;
import com.labelwall.mall.entity.School;
import com.labelwall.mall.service.IProvinceService;
import com.labelwall.mall.service.ISchoolService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
    @Autowired
    private IProvinceService provinceService;

    @Override
    public ResponseObject<List<School>> getSchoolByProvinceId(Integer provinceId) {
        List<School> schoolList = schoolMapper.getSchoolListByProvinceId(provinceId);
        if (CollectionUtils.isEmpty(schoolList)) {
            return ResponseObject.fail(ResponseStatus.FAIL.getCode(),
                    ResponseStatus.FAIL.getValue());
        }
        return ResponseObject.successStautsData(schoolList);
    }

    @Override
    public String findNameBySchoolId(Integer schoolId) {
        if (schoolId != null) {
            String schoolName = schoolMapper.findNameBySchoolId(schoolId);
            return schoolName;
        }
        return null;
    }

    @Override
    public ResponseObject<List<School>> getSchoolList(String provinceName) {
        if (StringUtils.isBlank(provinceName)) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        Integer provinceId = provinceService.findIdByProvinceName(provinceName);
        if (provinceId != null) {
            return getSchoolByProvinceId(provinceId);
        }
        return ResponseObject.failStatusMessage("获取内容失败");
    }
}
