package com.labelwall.mall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.ProductDto;
import com.labelwall.mall.dto.ShopDto;
import com.labelwall.mall.dto.ShopServicesDto;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.entity.ServiceType;
import com.labelwall.mall.entity.ShopServices;
import com.labelwall.mall.entity.TradeType;
import com.labelwall.mall.service.*;
import com.labelwall.util.CookieUtil;
import com.labelwall.util.JsonUtil;
import com.labelwall.util.RedisPoolUtil;
import com.labelwall.util.storage.QiniuStorage;
import com.labelwall.util.storage.ShopEnum;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 开店controller
 * Created by Administrator on 2017-12-06.
 */
@RestController
@RequestMapping("/shop")
public class ShopController {
	
	private static final Logger logger = LoggerFactory.getLogger(ShopController.class);

    @Autowired
    private IShopService shopService;
    @Autowired
    private IProductService productService;
    @Autowired
    private IShopServiceService shopServiceService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IOrderService orderService;

    /**
     * 获取商铺列表，查询条件（keyword,user_id,category_id,level）
     *
     * @param shopDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/get_shop_list/{pageNum}/{pageSize}", method = RequestMethod.POST)
    public ResponseObject<PageInfo> getShopList(ShopDto shopDto,
												@PathVariable(value = "pageNum") Integer pageNum,
												@PathVariable(value = "pageSize") Integer pageSize) {
    	List<Integer> shopIdListOnService=null;
    	List<Integer> shopIdListOnProduct=null;
    	if(shopDto.getServiceId()!=null){
    		shopIdListOnService=shopService.getShopOnServiceId(shopDto.getServiceId());
    		if(shopIdListOnService.size()==0){
    			shopIdListOnService.add(0);
    		}
    	}
    	if(shopDto.getProductId()!=null){
    		shopIdListOnProduct=shopService.getShopOnProductId(shopDto.getProductId());
    		if(shopIdListOnProduct.size()==0){
    			shopIdListOnProduct.add(0);
    		}
    	}
    	PageHelper.startPage(pageNum, pageSize);
    	List<ShopDto> shopList = shopService.getShopList(shopDto,shopIdListOnService,shopIdListOnProduct);
    	PageInfo pageInfo = new PageInfo(shopList);
        return ResponseObject.successStautsData(pageInfo);
    }

   

    /**
     * 根据id获取商铺详情
     *
     * @param shopId
     * @return
     */
    @RequestMapping(value = "/get_shop_detail/{shopId}", method = RequestMethod.GET)
    public ResponseObject<ShopDto> getShopDetail(@PathVariable(value = "shopId") Integer shopId) {
        return shopService.getShopDetail(shopId);
    }
    
    //获得当前用户的商铺信息
    @RequestMapping(value="/getMyshop",method=RequestMethod.GET)
    public ResponseObject<ShopDto> getMyShop(HttpServletRequest request){
    	String loginToken= CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json= RedisPoolUtil.get(loginToken);
    	UserDto userDto= JsonUtil.stringToObj(json, UserDto.class);
    	ShopDto shop=shopService.getShopDetailByUserId(userDto.getId());
    	if(shop==null){
    		return ResponseObject.fail(ShopEnum.NO_SHOP.getState(), ShopEnum.NO_SHOP.getStateInfo());
    	}else{
    		return ResponseObject.successStautsData(shop);
    	}
    }
   
    //开店前判断当前用户是否可以开店
    @RequestMapping(value="/judge",method=RequestMethod.GET)
    public ResponseObject<ShopEnum> judge(HttpServletRequest request){
    	String loginToken= CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json= RedisPoolUtil.get(loginToken);
    	UserDto userDto= JsonUtil.stringToObj(json, UserDto.class);
    	if(userDto.getUsername()==null||userDto.getGender()==null||userDto.getPhone()==null){
    		return ResponseObject.fail(ShopEnum.USERINFO_LACK.getState(), ShopEnum.USERINFO_LACK.getStateInfo());
    	}
    	ShopDto shop=shopService.getShopDetailByUserId(userDto.getId());
    	if(shop!=null){
    		return ResponseObject.fail(ShopEnum.REPEAT.getState(), ShopEnum.REPEAT.getStateInfo());
    	}
    	return ResponseObject.successStatusMessage(ShopEnum.SUCCESS.getStateInfo());
    }
    

    
    //根据是校园服务还是社区服务的类型  查询可供选择的服务
    @RequestMapping(value="/getServiceList/{type}",method=RequestMethod.GET)
    public ResponseObject<List<ServiceType>> getServiceList(@PathVariable("type")Integer type){
    	List<ServiceType> list=shopServiceService.getServiceListByType(type);
    	if(list.size()>0){
    		return ResponseObject.successStautsData(list);
    	}
    	return ResponseObject.fail(ShopEnum.NO_DATA.getState(), ShopEnum.NO_DATA.getStateInfo());
    }
    
    //开服务类型  商铺
    @RequestMapping(value="/openServiceShop",method=RequestMethod.POST)
    @ResponseBody
    public ResponseObject<ShopEnum> openServiceShop(ShopDto shop, ShopServicesDto service, HttpServletRequest request){
    	if(shop.getShopType()!=0){
    		return ResponseObject.fail(ShopEnum.DATA_PROBLEM.getState(), ShopEnum.DATA_PROBLEM.getStateInfo());
    	}else{
    		try {
    			if(shop.getLocationType()==0){
        			if(shop.getName()==null||shop.getDescription()==null||shop.getSchoolId()==0||shop.getPhone()==null||service.getServiceId()==null||service.getPrice()==null||service.getTheme()==null){
        				return ResponseObject.fail(ShopEnum.DATA_DEFECT.getState(), ShopEnum.DATA_DEFECT.getStateInfo());
        			}
        		}else{
        			if(shop.getName()==null||shop.getDescription()==null||shop.getPhone()==null||service.getPrice()==null||service.getTheme()==null){
        				return ResponseObject.fail(ShopEnum.DATA_DEFECT.getState(), ShopEnum.DATA_DEFECT.getStateInfo());
        			}
        		}
			} catch (Exception e) {
				return ResponseObject.fail(-9, "有字段为空，请检查");
			}
    	}
    	//存储图片处理
        if(shop.getShopImg()!=null&&shop.getShopImg().getSize()>0){
        	try {
				byte[] shopImg=shop.getShopImg().getBytes();
				String shopImgKey = QiniuStorage.uploadShopTopImg(shopImg);
				shop.setTopImgURL(shopImgKey);
			} catch (IOException e) {
				logger.error("商铺主题图片上传失败", e);
				return ResponseObject.fail(ShopEnum.SHOP_IMG_FAIL.getState(), ShopEnum.SHOP_IMG_FAIL.getStateInfo());
			}
        }
        if(service.getServiceImg()!=null&&service.getServiceImg().getSize()>0){
        	try {
				byte[] serviceImg=service.getServiceImg().getBytes();
				String serviceImgKey = QiniuStorage.uploadServiceImg(serviceImg);
				service.setImgURL(serviceImgKey);
			} catch (IOException e) {
				logger.error("服务图片上传失败", e);
				return ResponseObject.fail(ShopEnum.SERVICE_IMG_FAIL.getState(), ShopEnum.SERVICE_IMG_FAIL.getStateInfo());
			}
        }
        String loginToken= CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json= RedisPoolUtil.get(loginToken);
    	UserDto userDto= JsonUtil.stringToObj(json, UserDto.class);
    	
        int result=shopService.openServiceShop(shop,service,userDto);
        if(result>0){
        	return ResponseObject.successStatus();
        }else{
        	return ResponseObject.fail(ShopEnum.ERRO.getState(), ShopEnum.ERRO.getStateInfo());
        }
    }
    /**
     * 开书店或者小卖部类型  商铺
     */
    @RequestMapping(value="/openTradeShop",method=RequestMethod.POST)
    @ResponseBody
    public ResponseObject<ShopEnum> openTradeShop(ShopDto shop, HttpServletRequest request){
    	if(shop.getShopType()!=1){
    		return ResponseObject.fail(ShopEnum.DATA_PROBLEM.getState(), ShopEnum.DATA_PROBLEM.getStateInfo());
    	}else{
    		if(shop.getLocationType()==0){
    			if(shop.getTradeType()==null||shop.getName()==null||shop.getDescription()==null||shop.getSchoolId()==null||shop.getPhone()==null){
    				return ResponseObject.fail(ShopEnum.DATA_DEFECT.getState(), ShopEnum.DATA_DEFECT.getStateInfo());
    			}
    		}else{
    			if(shop.getTradeType()==null||shop.getName()==null||shop.getDescription()==null||shop.getProvinceId()==null||shop.getCityId()==null||shop.getCountyId()==null||shop.getPhone()==null){
    				return ResponseObject.fail(ShopEnum.DATA_DEFECT.getState(), ShopEnum.DATA_DEFECT.getStateInfo());
    			}
    		}
    	}
    	//存储图片处理
    	if(shop.getShopImg()!=null&&shop.getShopImg().getSize()>0){
        	try {
				byte[] shopImg=shop.getShopImg().getBytes();
				String shopImgKey = QiniuStorage.uploadShopTopImg(shopImg);
				shop.setTopImgURL(shopImgKey);
			} catch (IOException e) {
				logger.error("商铺主题图片上传失败", e);
				return ResponseObject.fail(ShopEnum.SHOP_IMG_FAIL.getState(), ShopEnum.SHOP_IMG_FAIL.getStateInfo());
			}
        }
        String loginToken= CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json= RedisPoolUtil.get(loginToken);
    	UserDto userDto= JsonUtil.stringToObj(json, UserDto.class);
        int result=shopService.openTradeShop(shop,userDto);
        if(result>0){
        	return ResponseObject.successStatus();
        }else{
        	return ResponseObject.fail(ShopEnum.ERRO.getState(), ShopEnum.ERRO.getStateInfo());
        }
    }
   

    
    //添加商品
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public ResponseObject<ShopEnum> add(ProductDto dto, HttpServletRequest request){
    	String loginToken= CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json= RedisPoolUtil.get(loginToken);
    	UserDto userDto= JsonUtil.stringToObj(json, UserDto.class);
    	Integer shopId=shopService.getShopDetailByUserId(userDto.getUserId()).getId();
    	dto.setShopId(shopId);
    	dto.setCreateTime(new Date());
    	dto.setUpdateTime(new Date());
    	dto.setStatus(1);
    	//存储图片处理
        if(dto.getMainImg()!=null&&dto.getMainImg().getSize()>0){
        	try {
				byte[] productMainImg = dto.getMainImg().getBytes();
				String productMainImgKey = QiniuStorage.uploadProductMainImage(productMainImg);
				dto.setMainImage(productMainImgKey);
			} catch (IOException e) {
				logger.error("图片上传失败", e);
				ResponseObject.fail(-101, "图片上传失败");
			}
        }
    	if(dto.getSubImg()!=null&&dto.getSubImg().getSize()>0){
    		try {
				byte[] productSubImg = dto.getSubImg().getBytes();
				String productSubImgKey = QiniuStorage.uploadProductSubImage(productSubImg);
				dto.setSubImages(productSubImgKey);
			} catch (IOException e) {
				logger.error("图片上传失败", e);
				ResponseObject.fail(-101, "图片上传失败");
			}
    	}
    	int result=productService.add(dto);
    	if(result>0){
//    		model.setViewName("redirect:/shop/getMyshop");
    		return ResponseObject.successStatus();
    	}else{
//    		model.setViewName("shop/addProductUI");
    		return ResponseObject.fail(ShopEnum.ADD_FAIL.getState(), ShopEnum.ADD_FAIL.getStateInfo());
    	}
    }
    
//    /*
//     * 购买商品，生成订单，减库存
//     */
//    @RequestMapping(value="doShooping/{shoopingId}",method=RequestMethod.GET)
//    public ResponseObject<OrderVo> doShooping(HttpSession session,
//            									@PathVariable("shoppingId") Integer shoppingId){
//    	UserDto userDto = getUser();
//    	return orderService.doShooping(userDto.getId(), shoppingId);
//    }
    
    /**
     * 给商铺添加服务    
     */
    @RequestMapping(value="/addService/{shopId}/{serviceId}",method=RequestMethod.POST)
    public ResponseObject<ShopEnum> addService(ShopServicesDto service, HttpServletRequest request){
    	ShopDto data = shopService.getShopDetail(service.getShopId()).getData();
    	for (ShopServices item : data.getServiceList()) {
			if(item.getServiceId()==service.getServiceId()){
				return ResponseObject.fail(-11, "请勿重复添加服务");
			}
		}
    	ServiceType tmp=shopServiceService.getServiceById(service.getServiceId());
    	if(data!=null && tmp!= null && data.getShopType()==0){
    		service.setServiceName(tmp.getName());
    		service.setCreateTime(new Date());
    		service.setUpdateTime(new Date());
    		//存储图片处理
    		if(service.getServiceImg()!=null&&service.getServiceImg().getSize()>0){
            	try {
    				byte[] serviceImg=service.getServiceImg().getBytes();
    				String serviceImgKey = QiniuStorage.uploadServiceImg(serviceImg);
    				service.setImgURL(serviceImgKey);
    			} catch (IOException e) {
    				logger.error("服务图片上传失败", e);
    				return ResponseObject.fail(ShopEnum.SERVICE_IMG_FAIL.getState(), ShopEnum.SERVICE_IMG_FAIL.getStateInfo());
    			}
            }
            //插入数据库
            int result=shopService.insertShopService(service);
            if(result>0){
            	return ResponseObject.successStatus();
            }else{
            	return ResponseObject.fail(ShopEnum.ERRO.getState(), ShopEnum.ERRO.getStateInfo());
            }
    	}else{
    		return ResponseObject.fail(ShopEnum.DATA_PROBLEM.getState(), ShopEnum.DATA_PROBLEM.getStateInfo());
    	}
    }
    
    /**
     * 删除自己商铺中的某件商品
     * @return
     */
    @RequestMapping(value="/deleteProduct/{productId}",method=RequestMethod.GET)
    public ResponseObject<String> deletePro(@PathVariable("productId")Integer productId, HttpServletRequest request){
    	String loginToken= CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json= RedisPoolUtil.get(loginToken);
    	UserDto userDto= JsonUtil.stringToObj(json, UserDto.class);
    	ShopDto shopDetailByUserId = shopService.getShopDetailByUserId(userDto.getUserId());
    	return shopService.deletePro(shopDetailByUserId,productId);
    }
    
    /**
     * 删除自己商铺中的某项服务
     * @return
     */
    @RequestMapping(value="/deleteService/{serviceId}",method=RequestMethod.GET)
   public ResponseObject<String> deleteService(@PathVariable("serviceId")Integer serviceId, HttpServletRequest request){
    	String loginToken= CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json= RedisPoolUtil.get(loginToken);
    	UserDto userDto= JsonUtil.stringToObj(json, UserDto.class);
    	ShopDto shopDetailByUserId = shopService.getShopDetailByUserId(userDto.getUserId());
    	return shopService.deleteService(shopDetailByUserId,serviceId);
    }
    
    /**
     * 获得所有交易类型商铺的种类
     * @return
     */
    @RequestMapping(value="/getAllTradeType",method=RequestMethod.GET)
    public ResponseObject<List<TradeType>> getAllTradeType(){
    	List<TradeType> result=shopService.getAllTradeType();
    	return ResponseObject.successStautsData(result);
    }
    
    
}
