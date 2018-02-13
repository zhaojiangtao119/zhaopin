package com.labelwall.mall.dao;

import com.labelwall.mall.entity.ServiceType;
import com.labelwall.mall.entity.ShopServices;

import java.util.List;

public interface ShopServiceMapper {

	List<ShopServices> getServiceListByShopId(Integer id);

	List<ServiceType> getServiceListByType(int type);

	int insertService(ShopServices service);

	ServiceType getServiceTypeById(Integer serviceId);

}
