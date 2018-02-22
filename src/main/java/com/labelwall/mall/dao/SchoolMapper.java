package com.labelwall.mall.dao;

import com.labelwall.mall.entity.School;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SchoolMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(School record);

    int insertSelective(School record);

    School selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(School record);

    int updateByPrimaryKey(School record);

    List<School> getSchoolListByProvinceId(Integer provinceId);

    /**
     * 通过主键学校的名称
     *
     * @param schoolId
     * @return
     */
    String findNameBySchoolId(Integer schoolId);

    List<School> getSchoolList();

    /**
     * 获取学校id
     *
     * @param provinceName
     * @param schoolName
     * @return
     */
    Integer getSchoolIdByProNameSchName(@Param("provinceName") String provinceName,
                                      @Param("schoolName") String schoolName);
}