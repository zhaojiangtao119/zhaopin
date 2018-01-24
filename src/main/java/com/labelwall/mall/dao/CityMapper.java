package com.labelwall.mall.dao;

import com.labelwall.mall.entity.City;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);

    List<City> getCityListByProvinceId(Integer provinceId);

    Integer findIdByCityName(@Param("provinceId") Integer provinceId, @Param("name") String name);

}