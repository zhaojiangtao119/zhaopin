package com.labelwall.mall.service.impl;

import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.mall.dao.*;
import com.labelwall.mall.dto.ProductDto;
import com.labelwall.mall.dto.ShopDto;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.entity.Shop;
import com.labelwall.mall.entity.ShopServices;
import com.labelwall.mall.service.IShopService;
import com.labelwall.util.storage.QiniuStorage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017-12-06.
 */
@Service("shopService")
public class ShopServiceImpl implements IShopService {

    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private ProvinceMapper provinceMapper;
    @Autowired
    private CityMapper cityMapper;
    @Autowired
    private CountyMapper countyMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ShopServiceMapper shopServiceMapper;
    @Autowired
    private SchoolMapper schoolMapper;

    @Override
    public List<ShopDto> getShopList(ShopDto shopDto, List<Integer> shopIdListOnService, List<Integer> shopIdListOnProduct) {

        List<ShopDto> shopDtoList = shopMapper.getShopList(shopDto, shopIdListOnService, shopIdListOnProduct);
        if (CollectionUtils.isNotEmpty(shopDtoList)) {
            //加载店主的头像
            for (ShopDto item : shopDtoList) {
                if (StringUtils.isNotBlank(item.getUserDto().getHead())) {
                    item.getUserDto().setHead(QiniuStorage.getUserHeadUrl(item.getUserDto().getHead()));
                } else {
                    item.getUserDto().setHead(QiniuStorage.getUserHeadUrl(Const.DEFAULT_USER_HEAD));
                }
            }
        }
        return shopDtoList;
    }

    @Override
    public ResponseObject<ShopDto> getShopDetail(Integer shopId) {
        if (shopId == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        ShopDto shopDto = shopMapper.selectByPrimaryKey(shopId);
        if (shopDto != null) {
            ProductDto productDto = new ProductDto();
            productDto.setShopId(shopDto.getId());
            List<ProductDto> productList = productMapper.getShopProductList(productDto);
            shopDto.setProductList(productList);

            List<ShopServices> serviceList = shopServiceMapper.getServiceListByShopId(shopDto.getId());
            shopDto.setServiceList(serviceList);
            return ResponseObject.successStautsData(shopDto);
        }
        return ResponseObject.fail(ResponseStatus.FAIL.getCode(), ResponseStatus.FAIL.getValue());
    }

    @Override
    public ShopDto getShopDetailByUserId(Integer id) {
        ShopDto shopDto = new ShopDto();
        shopDto.setUserId(id);
        List<ShopDto> shopList = shopMapper.getShopList(shopDto, null, null);
        if (shopList.size() > 0) {
            ShopDto dto = shopList.get(0);

            ProductDto productDto = new ProductDto();
            productDto.setShopId(dto.getId());
            List<ProductDto> productList = productMapper.getShopProductList(productDto);
            dto.setProductList(productList);

            List<ShopServices> serviceList = shopServiceMapper.getServiceListByShopId(dto.getId());
            dto.setServiceList(serviceList);
            return dto;
        }
        return null;
    }

    @Override
    public String getlocation(Integer provinceId, Integer cityId, Integer countyId) {
        String province = provinceMapper.getNameById(provinceId);
        String city = cityMapper.getNameById(cityId);
        String county = countyMapper.getNameById(countyId);
        return province + "省" + city + "市" + county + "区";
    }

    @Override
    @Transactional
    public int openServiceShop(Shop shop, ShopServices service, UserDto user) {
        shop.setUserId(user.getUserId());
        shop.setCreateTime(new Date());
        shop.setUpdateTime(new Date());
        if (shop.getShopType() == 0) {
            shop.setTypeName("服务");
            if (shop.getLocationType() == 0) {
                shop.setLocationName("校园");
                String schoolName = schoolMapper.selectByPrimaryKey(shop.getSchoolId()).getName();
                shop.setSchoolName(schoolName);
                shop.setProvinceId(user.getProvinceId());
                shop.setCityId(user.getCityId());
                shop.setCountyId(user.getCountyId());

            } else {
                shop.setLocationName("社区");
            }
        } else {
            shop.setTypeName("商店");
            if (shop.getLocationType() == 0) {
                shop.setLocationName("校园");
                String schoolName = schoolMapper.selectByPrimaryKey(shop.getSchoolId()).getName();
                shop.setSchoolName(schoolName);
                shop.setProvinceId(user.getProvinceId());
                shop.setCityId(user.getCityId());
                shop.setCountyId(user.getCountyId());
            } else {
                shop.setLocationName("社区");
            }
        }
        shop.setStatus(1);
        shop.setLevel(1);
        shop.setProductCategoryId(0);
        //将商铺信息插入wall_shop表
        int shopId = shopMapper.insert(shop);
        if (shopId > 0) {
            //将提供的服务内容插入wall_shop_service
            service.setShopId(shop.getId());
            String serviceName = shopServiceMapper.getServiceTypeById(service.getServiceId()).getName();
            service.setServiceName(serviceName);
            service.setCreateTime(new Date());
            service.setUpdateTime(new Date());
            int serviceResult = shopServiceMapper.insertService(service);
            return serviceResult;
        }
        return 0;
    }

    @Override
    @Transactional
    public int openTradeShop(Shop shop, UserDto user) {
        shop.setUserId(user.getUserId());
        shop.setCreateTime(new Date());
        shop.setUpdateTime(new Date());
        if (shop.getShopType() == 0) {
            shop.setTypeName("服务");
            if (shop.getLocationType() == 0) {
                shop.setLocationName("校园");
                String schoolName = schoolMapper.selectByPrimaryKey(shop.getSchoolId()).getName();
                shop.setSchoolName(schoolName);
                shop.setProvinceId(user.getProvinceId());
                shop.setCityId(user.getCityId());
                shop.setCountyId(user.getCountyId());
            } else {
                shop.setLocationName("社区");
            }
        } else {
            shop.setTypeName("商店");
            if (shop.getLocationType() == 0) {
                shop.setLocationName("校园");
                String schoolName = schoolMapper.selectByPrimaryKey(shop.getSchoolId()).getName();
                shop.setSchoolName(schoolName);
                shop.setProvinceId(user.getProvinceId());
                shop.setCityId(user.getCityId());
                shop.setCountyId(user.getCountyId());
            } else {
                shop.setLocationName("社区");
            }
        }
        shop.setProductCategoryId(0);
        shop.setStatus(1);
        shop.setLevel(1);
        //将商铺信息插入wall_shop表
        int shopId = shopMapper.insert(shop);
        return shopId;
    }

    @Override
    public List<Integer> getShopOnServiceId(Integer serviceId) {
        return shopMapper.getShopOnServiceId(serviceId);
    }

    @Override
    public List<Integer> getShopOnProductId(Integer productId) {
        return shopMapper.getShopOnProductId(productId);
    }

    @Override
    public int insertShopService(ShopServices service) {
        return shopServiceMapper.insertService(service);
    }
}
