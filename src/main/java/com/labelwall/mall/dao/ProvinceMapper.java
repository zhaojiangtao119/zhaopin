package com.labelwall.mall.dao;

import com.labelwall.mall.entity.Province;

import java.util.List;

public interface ProvinceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Province record);

    int insertSelective(Province record);

    Province selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Province record);

    int updateByPrimaryKey(Province record);

    List<Province> getProvinceList();

    Integer findIdByProvinceName(String name);

    String getNameById(Integer id);
}