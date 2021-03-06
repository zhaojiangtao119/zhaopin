package com.labelwall.mall.service;

import com.labelwall.common.ResponseObject;
import com.labelwall.mall.entity.School;

import java.util.List;

/**
 * Created by Administrator on 2017-12-04.
 */
public interface ISchoolService {

    ResponseObject<List<School>> getSchoolByProvinceId(Integer provinceId);

    /**
     * 通过id获取学校的名称
     *
     * @param schoolId
     * @return
     */
    String findNameBySchoolId(Integer schoolId);

    /**
     * 根据省份名称查询该省份下的学校
     *
     * @param provicneName
     * @return
     */
    ResponseObject<List<School>> getSchoolList(String provicneName);

    /**
     * 根据省份id，学校名称，获取学校的id
     *
     * @param provinceName
     * @param schoolName
     * @return
     */
    Integer getSchoolIdByProNameSchName(String provinceName, String schoolName);
}
