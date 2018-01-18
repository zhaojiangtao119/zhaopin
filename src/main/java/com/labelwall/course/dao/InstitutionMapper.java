package com.labelwall.course.dao;


import com.labelwall.course.dto.InstitutionDto;
import com.labelwall.course.entity.Institution;

import java.util.List;

/**
 * Created by Administrator on 2017-12-26.
 */
public interface InstitutionMapper {
    /**
     * 获取5个推荐课程
     *
     * @return
     */
    List<Institution> getRecommed();

    /**
     * 获取机构
     *
     * @param institutionDto
     * @return
     */
    List<Institution> getInstitutions(InstitutionDto institutionDto);

    /**
     * 通过主键获取对象
     *
     * @param institutionId
     * @return
     */
    Institution selectByPrimaryKey(Integer institutionId);
}
