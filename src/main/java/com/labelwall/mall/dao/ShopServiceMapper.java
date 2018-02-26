package com.labelwall.mall.dao;

import com.labelwall.mall.entity.ServiceType;
import com.labelwall.mall.entity.ShopServices;

import java.util.List;

public interface ShopServiceMapper {


	//获得所有的可供选择的服务类型
	List<ServiceType> getServiceListByType(int type);
	ServiceType getServiceTypeById(Integer serviceId);

	
	
	List<ShopServices> getServiceListByShopId(Integer id);
	
	int insertService(ShopServices service);
	//根据ID获得服务详情
	ShopServices getServiceDetailById(Integer serviceId);
	//根据ID删除服务
	int deleteServiceById(Integer serviceId);

}
