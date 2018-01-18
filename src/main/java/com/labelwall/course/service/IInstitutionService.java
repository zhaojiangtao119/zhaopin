package com.labelwall.course.service;


import com.github.pagehelper.PageInfo;
import com.labelwall.common.ResponseObject;
import com.labelwall.course.dto.InstitutionDto;
import com.labelwall.course.entity.Institution;

import java.util.List;

/**
 * Created by Administrator on 2017-12-26.
 */

public interface IInstitutionService {
    /**
     * 获取推荐的机构
     *
     * @return
     */
    ResponseObject<List<Institution>> getRecommed();

    /**
     * 分页获取机构信息
     *
     * @param institutionDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseObject<PageInfo> getInstitutions(InstitutionDto institutionDto, Integer pageNum, Integer pageSize);

    /**
     * 获取机构详情
     *
     * @param institutionId
     * @return
     */
    ResponseObject<Institution> getInstitution(Integer institutionId);
}
