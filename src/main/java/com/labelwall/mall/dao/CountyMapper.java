package com.labelwall.mall.dao;

import com.labelwall.mall.entity.County;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CountyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(County record);

    int insertSelective(County record);

    County selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(County record);

    int updateByPrimaryKey(County record);

    /**
     * 根据provinceId和cityId获取区县信息
     *
     * @param provinceId
     * @param cityId
     * @return
     */
    List<County> getCountyListByProvinceIdCityId(@Param("provinceId") Integer provinceId, @Param("cityId") Integer cityId);

    /**
     * 获取区县id
     *
     * @param cityId
     * @param name
     * @return
     */
    Integer findByCountyName(@Param("cityId") Integer cityId, @Param("name") String name);
}