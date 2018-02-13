package com.labelwall.mall.service.impl;

import com.labelwall.mall.dao.ShopServiceMapper;
import com.labelwall.mall.entity.ServiceType;
import com.labelwall.mall.service.IShopServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("shopServiceServiceImpl")
public class ShopServiceServiceImpl implements IShopServiceService {

    @Autowired
    private ShopServiceMapper shopServiceMapper;

    @Override
    public List<ServiceType> getServiceListByType(int type) {
        return shopServiceMapper.getServiceListByType(type);
    }

    @Override
    public ServiceType getServiceById(Integer serviceId) {
        return shopServiceMapper.getServiceTypeById(serviceId);
    }

}
