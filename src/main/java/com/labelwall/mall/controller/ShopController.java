package com.labelwall.mall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ShopEnum;
import com.labelwall.mall.dto.ProductDto;
import com.labelwall.mall.dto.ShopDto;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.entity.ServiceType;
import com.labelwall.mall.entity.Shop;
import com.labelwall.mall.entity.ShopServices;
import com.labelwall.mall.service.*;
import com.labelwall.util.StringUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
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

    @Autowired
    private IShopService shopService;
    @Autowired
    private HttpSession session;
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
        List<Integer> shopIdListOnService = null;
        List<Integer> shopIdListOnProduct = null;
        if (shopDto.getServiceId() != null) {
            shopIdListOnService = shopService.getShopOnServiceId(shopDto.getServiceId());
            if (shopIdListOnService.size() == 0) {
                shopIdListOnService.add(0);
            }
        }
        if (shopDto.getProductId() != null) {
            shopIdListOnProduct = shopService.getShopOnProductId(shopDto.getProductId());
            if (shopIdListOnProduct.size() == 0) {
                shopIdListOnProduct.add(0);
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        List<ShopDto> shopList = shopService.getShopList(shopDto, shopIdListOnService, shopIdListOnProduct);
        PageInfo pageInfo = new PageInfo(shopList);
        return ResponseObject.successStautsData(pageInfo);
    }

    //
    @RequestMapping(value = "/getShop", method = RequestMethod.GET)
    public ModelAndView getShop(ModelAndView model) {
        model.setViewName("shop/getShop");
        return model;
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
    @RequestMapping(value = "/getMyshop", method = RequestMethod.GET)
    public ResponseObject<ShopDto> getMyShop() {
        UserDto user = getUser();
        ShopDto shop = shopService.getShopDetailByUserId(user.getId());
        if (shop == null) {
            return ResponseObject.fail(ShopEnum.NO_SHOP.getState(), ShopEnum.NO_SHOP.getStateInfo());
        } else {
            return ResponseObject.successStautsData(shop);
        }
    }

    //开店前判断当前用户是否可以开店
    @RequestMapping(value = "/judge", method = RequestMethod.GET)
    public ResponseObject<ShopEnum> judge() {
        UserDto user = getUser();
        if (user.getUsername() == null || user.getGender() == null || user.getPhone() == null) {
            return ResponseObject.fail(ShopEnum.USERINFO_LACK.getState(), ShopEnum.USERINFO_LACK.getStateInfo());
        }
        ShopDto shop = shopService.getShopDetailByUserId(user.getId());
        if (shop != null) {
            return ResponseObject.fail(ShopEnum.REPEAT.getState(), ShopEnum.REPEAT.getStateInfo());
        }
        return ResponseObject.successStatusMessage(ShopEnum.SUCCESS.getStateInfo());
    }

    //跳转至开店UI
    @RequestMapping("/openShopUI")
    public ModelAndView openShopUI(ModelAndView model) {
        model.setViewName("/shop/openShopUI");
        UserDto user = getUser();
        model.addObject("user", user);
        return model;
    }

    //根据是校园服务还是社区服务的类型  查询可供选择的服务
    @RequestMapping(value = "/getServiceList/{type}", method = RequestMethod.GET)
    public ResponseObject<List<ServiceType>> getServiceList(@PathVariable("type") Integer type) {
        List<ServiceType> list = shopServiceService.getServiceListByType(type);
        if (list.size() > 0) {
            return ResponseObject.successStautsData(list);
        }
        return ResponseObject.fail(ShopEnum.NO_DATA.getState(), ShopEnum.NO_DATA.getStateInfo());
    }

    //开服务类型  商铺
    @RequestMapping(value = "/openServiceShop", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject<ShopEnum> openServiceShop(Shop shop, ShopServices service, @RequestParam("poster") MultipartFile[] tmpFiles, HttpServletRequest request) {
        if (shop.getShopType() != 0) {
            return ResponseObject.fail(ShopEnum.DATA_PROBLEM.getState(), ShopEnum.DATA_PROBLEM.getStateInfo());
        } else {
            try {
                if (shop.getLocationType() == 0) {
                    if (shop.getName() == null || shop.getDescription() == null || shop.getSchoolId() == 0 || shop.getPhone() == null || service.getServiceId() == null || service.getPrice() == null || service.getTheme() == null) {
                        return ResponseObject.fail(ShopEnum.DATA_DEFECT.getState(), ShopEnum.DATA_DEFECT.getStateInfo());
                    }
                } else {
                    if (shop.getName() == null || shop.getDescription() == null || shop.getPhone() == null || service.getPrice() == null || service.getTheme() == null) {
                        return ResponseObject.fail(ShopEnum.DATA_DEFECT.getState(), ShopEnum.DATA_DEFECT.getStateInfo());
                    }
                }
            } catch (Exception e) {
                return ResponseObject.fail(-9, "有字段为空，请检查");
            }
        }
        //存储图片处理
        if (tmpFiles != null && tmpFiles.length > 0) {
            for (int i = 0; i < tmpFiles.length; i++) {
                // 获取物理路径
                String targetDirectory = request.getSession().getServletContext().getRealPath("/tmp");
                String tmpFileName = tmpFiles[i].getOriginalFilename(); // 上传的文件名
                int dot = tmpFileName.lastIndexOf('.');
                String ext = "";  //文件后缀名
                if ((dot > -1) && (dot < (tmpFileName.length() - 1))) {
                    ext = tmpFileName.substring(dot + 1);
                }
                // 其他文件格式不处理
                if ("png".equalsIgnoreCase(ext) || "jpg".equalsIgnoreCase(ext) || "gif".equalsIgnoreCase(ext)) {
                    // 重命名上传的文件名
                    String targetFileName = StringUtils.renameFileName(tmpFileName);
                    // 保存的新文件
                    File target = new File(targetDirectory, targetFileName);

                    try {
                        // 保存文件
                        FileUtils.copyInputStreamToFile(tmpFiles[i].getInputStream(), target);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (i == 1) {
                        service.setImgURL(request.getContextPath() + "/tmp/" + targetFileName);
                    } else {
                        shop.setTopImgURL(request.getContextPath() + "/tmp/" + targetFileName);
                    }
                }
            }
        }
        //TODO
        UserDto user = getUser();
        int result = shopService.openServiceShop(shop, service, user);
        if (result > 0) {
            return ResponseObject.successStatus();
        } else {
            return ResponseObject.fail(ShopEnum.ERRO.getState(), ShopEnum.ERRO.getStateInfo());
        }
    }

    /**
     * 开书店或者小卖部类型  商铺
     */
    @RequestMapping(value = "/openTradeShop", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject<ShopEnum> openTradeShop(Shop shop, @RequestParam("poster") MultipartFile tmpFile, HttpServletRequest request) {
        if (shop.getShopType() != 1) {
            return ResponseObject.fail(ShopEnum.DATA_PROBLEM.getState(), ShopEnum.DATA_PROBLEM.getStateInfo());
        } else {
            if (shop.getLocationType() == 0) {
                if (shop.getTradeType() == null || shop.getName() == null || shop.getDescription() == null || shop.getSchoolId() == null || shop.getPhone() == null) {
                    return ResponseObject.fail(ShopEnum.DATA_DEFECT.getState(), ShopEnum.DATA_DEFECT.getStateInfo());
                }
            } else {
                if (shop.getTradeType() == null || shop.getName() == null || shop.getDescription() == null || shop.getProvinceId() == null || shop.getCityId() == null || shop.getCountyId() == null || shop.getPhone() == null) {
                    return ResponseObject.fail(ShopEnum.DATA_DEFECT.getState(), ShopEnum.DATA_DEFECT.getStateInfo());
                }
            }
        }
        //存储图片处理
        if (tmpFile != null) {
            // 获取物理路径
            String targetDirectory = request.getSession().getServletContext().getRealPath("/tmp");
            String tmpFileName = tmpFile.getOriginalFilename(); // 上传的文件名
            int dot = tmpFileName.lastIndexOf('.');
            String ext = "";  //文件后缀名
            if ((dot > -1) && (dot < (tmpFileName.length() - 1))) {
                ext = tmpFileName.substring(dot + 1);
            }
            // 其他文件格式不处理
            if ("png".equalsIgnoreCase(ext) || "jpg".equalsIgnoreCase(ext) || "gif".equalsIgnoreCase(ext)) {
                // 重命名上传的文件名
                String targetFileName = StringUtils.renameFileName(tmpFileName);
                // 保存的新文件
                File target = new File(targetDirectory, targetFileName);

                try {
                    // 保存文件
                    FileUtils.copyInputStreamToFile(tmpFile.getInputStream(), target);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                shop.setTopImgURL(request.getContextPath() + "/tmp/" + targetFileName);
            }
        }
        //       TODO
        UserDto user = getUser();
        int result = shopService.openTradeShop(shop, user);
        if (result > 0) {
            return ResponseObject.successStatus();
        } else {
            return ResponseObject.fail(ShopEnum.ERRO.getState(), ShopEnum.ERRO.getStateInfo());
        }
    }


    //添加商品UI
    @RequestMapping("/addUI")
    public ModelAndView addUI(ModelAndView model) {
        model.setViewName("shop/addProductUI");
        return model;
    }

    //添加商品
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseObject<ShopEnum> add(ProductDto dto,@RequestParam("poster") MultipartFile[] tmpFiles, HttpServletRequest request) {
        UserDto user = getUser();
        Integer shopId = shopService.getShopDetailByUserId(user.getUserId()).getId();
        dto.setShopId(shopId);
        dto.setCreateTime(new Date());
        dto.setUpdateTime(new Date());
        dto.setStatus(1);
        //存储图片处理
        if (tmpFiles != null && tmpFiles.length > 0) {
            for (int i = 0; i < tmpFiles.length; i++) {
                // 获取物理路径
                String targetDirectory = request.getSession().getServletContext().getRealPath("/tmp");
                String tmpFileName = tmpFiles[i].getOriginalFilename(); // 上传的文件名
                int dot = tmpFileName.lastIndexOf('.');
                String ext = "";  //文件后缀名
                if ((dot > -1) && (dot < (tmpFileName.length() - 1))) {
                    ext = tmpFileName.substring(dot + 1);
                }
                // 其他文件格式不处理
                if ("png".equalsIgnoreCase(ext) || "jpg".equalsIgnoreCase(ext) || "gif".equalsIgnoreCase(ext)) {
                    // 重命名上传的文件名
                    String targetFileName = StringUtils.renameFileName(tmpFileName);
                    // 保存的新文件
                    File target = new File(targetDirectory, targetFileName);

                    try {
                        // 保存文件
                        FileUtils.copyInputStreamToFile(tmpFiles[i].getInputStream(), target);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (i == 1) {
                        dto.setMainImage(request.getContextPath() + "/tmp/" + targetFileName);
                    } else {
                        dto.setSubImages(request.getContextPath() + "/tmp/" + targetFileName);
                    }
                }
            }
        }

        int result = productService.add(dto);
        if (result > 0) {
//    		model.setViewName("redirect:/shop/getMyshop");
            return ResponseObject.successStatus();
        } else {
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
    @RequestMapping(value = "/addService/{shopId}/{serviceId}", method = RequestMethod.POST)
    public ResponseObject<ShopEnum> addService(ShopServices service, @RequestParam("poster") MultipartFile tmpFile, HttpServletRequest request) {
        ShopDto data = shopService.getShopDetail(service.getShopId()).getData();
        for (ShopServices item : data.getServiceList()) {
            if (item.getServiceId() == service.getServiceId()) {
                return ResponseObject.fail(-11, "请勿重复添加服务");
            }
        }
        ServiceType tmp = shopServiceService.getServiceById(service.getServiceId());
        if (data != null && tmp != null && data.getShopType() == 0) {
            service.setServiceName(tmp.getName());
            service.setCreateTime(new Date());
            service.setUpdateTime(new Date());
            //存储图片处理
            if (tmpFile != null) {
                // 获取物理路径
                String targetDirectory = request.getSession().getServletContext().getRealPath("/tmp");
                String tmpFileName = tmpFile.getOriginalFilename(); // 上传的文件名
                int dot = tmpFileName.lastIndexOf('.');
                String ext = "";  //文件后缀名
                if ((dot > -1) && (dot < (tmpFileName.length() - 1))) {
                    ext = tmpFileName.substring(dot + 1);
                }
                // 其他文件格式不处理
                if ("png".equalsIgnoreCase(ext) || "jpg".equalsIgnoreCase(ext) || "gif".equalsIgnoreCase(ext)) {
                    // 重命名上传的文件名
                    String targetFileName = StringUtils.renameFileName(tmpFileName);
                    // 保存的新文件
                    File target = new File(targetDirectory, targetFileName);

                    try {
                        // 保存文件
                        FileUtils.copyInputStreamToFile(tmpFile.getInputStream(), target);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    service.setImgURL(request.getContextPath() + "/tmp/" + targetFileName);
                }
            }
            //插入数据库
            int result = shopService.insertShopService(service);
            if (result > 0) {
                return ResponseObject.successStatus();
            } else {
                return ResponseObject.fail(ShopEnum.ERRO.getState(), ShopEnum.ERRO.getStateInfo());
            }
        } else {
            return ResponseObject.fail(ShopEnum.DATA_PROBLEM.getState(), ShopEnum.DATA_PROBLEM.getStateInfo());
        }
    }

    //绑定当前用户的信息
    public UserDto getUser() {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto == null) {
            ResponseObject<UserDto> response = userService.login("zhangsan", "123456789");
            return response.getData();
        }
        return userDto;
    }
}
