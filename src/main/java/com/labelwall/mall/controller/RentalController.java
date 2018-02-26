package com.labelwall.mall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ShopEnum;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.entity.*;
import com.labelwall.mall.service.IRentalService;
import com.labelwall.mall.service.IUserService;
import com.labelwall.util.CookieUtil;
import com.labelwall.util.FileNameUtils;
import com.labelwall.util.JsonUtil;
import com.labelwall.util.RedisPoolUtil;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 租售电脑controller
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/rental")
@ResponseBody
public class RentalController {
    @Autowired
    private IRentalService rentalService;
    @Autowired
    private IUserService userService;
    @Autowired
    private HttpSession session;

    /**
     * 获得所有的可租售产品种类
     */
    @RequestMapping(value = "/getAllType", method = RequestMethod.GET)
    public ResponseObject<List<RentalType>> getAllType() {
        List<RentalType> result = rentalService.getAllType();
        return ResponseObject.successStautsData(result);
    }


    /**
     * 入驻开租售类店
     *
     * @return
     */
    @RequestMapping(value = "/openRentalShop", method = RequestMethod.POST)
    public ResponseObject<ShopEnum> openRentalShop(RentalShop shop, @RequestParam("poster") MultipartFile tmpFile, HttpServletRequest request) {
        if (shop.getLocationType() == 0) {
            if (shop.getName() == null || shop.getDescription() == null || shop.getSchoolId() == 0 || shop.getPhone() == null) {
                return ResponseObject.fail(ShopEnum.DATA_DEFECT.getState(), ShopEnum.DATA_DEFECT.getStateInfo());
            }
        } else {
            if (shop.getName() == null || shop.getDescription() == null || shop.getPhone() == null || shop.getProvinceId() == null || shop.getCityId() == null || shop.getCountyId() == null) {
                return ResponseObject.fail(ShopEnum.DATA_DEFECT.getState(), ShopEnum.DATA_DEFECT.getStateInfo());
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
                String targetFileName = FileNameUtils.renameFileName(tmpFileName);
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
        String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        int result = rentalService.openShop(shop, userDto);
        if (result > 0) {
            return ResponseObject.successStatus();
        } else {
            return ResponseObject.fail(ShopEnum.ERRO.getState(), ShopEnum.ERRO.getStateInfo());
        }
    }

    /**
     * 获取所有的CPU种类
     *
     * @return
     */
    @RequestMapping(value = "/getAllCPU", method = RequestMethod.GET)
    public ResponseObject<List<RentalCPU>> getAllCPU(RentalCPU cpu) {
        List<RentalCPU> result = rentalService.getAllCPU(cpu);
        return ResponseObject.successStautsData(result);
    }

    /**
     * 获取所有的内存种类
     *
     * @return
     */
    @RequestMapping(value = "/getAllmemory", method = RequestMethod.GET)
    public ResponseObject<List<RentalMemory>> getAllmemory(RentalMemory me) {
        List<RentalMemory> result = rentalService.getAllmemory(me);
        return ResponseObject.successStautsData(result);
    }

    /**
     * 获取所有的显卡种类
     *
     * @return
     */
    @RequestMapping(value = "/getAllcard", method = RequestMethod.GET)
    public ResponseObject<List<RentalCard>> getAllcard(RentalCard me) {
        List<RentalCard> result = rentalService.getAllcard(me);
        return ResponseObject.successStautsData(result);
    }

    /**
     * 获取所有的硬盘种类
     *
     * @return
     */
    @RequestMapping(value = "/getAlldisk", method = RequestMethod.GET)
    public ResponseObject<List<RentalDisk>> getAlldisk(RentalDisk me) {
        List<RentalDisk> result = rentalService.getAlldisk(me);
        return ResponseObject.successStautsData(result);
    }

    /**
     * 获取所有的主板种类
     *
     * @return
     */
    @RequestMapping(value = "/getAllboard", method = RequestMethod.GET)
    public ResponseObject<List<RentalBoard>> getAllboard(RentalBoard me) {
        List<RentalBoard> result = rentalService.getAllboard(me);
        return ResponseObject.successStautsData(result);
    }

    /**
     * 获取所有的电源种类
     *
     * @return
     */
    @RequestMapping(value = "/getAllpower", method = RequestMethod.GET)
    public ResponseObject<List<RentalPower>> getAllpower(RentalPower me) {
        List<RentalPower> result = rentalService.getAllpower(me);
        return ResponseObject.successStautsData(result);
    }

    /**
     * 获取所有的机箱种类
     *
     * @return
     */
    @RequestMapping(value = "/getAllcase", method = RequestMethod.GET)
    public ResponseObject<List<RentalCase>> getAllcase(RentalCase me) {
        List<RentalCase> result = rentalService.getAllcase(me);
        return ResponseObject.successStautsData(result);
    }

    /**
     * 获取所有的散热器种类
     *
     * @return
     */
    @RequestMapping(value = "/getAllradiator", method = RequestMethod.GET)
    public ResponseObject<List<RentalRadiator>> getAllradiator(RentalRadiator me) {
        List<RentalRadiator> result = rentalService.getAllradiator(me);
        return ResponseObject.successStautsData(result);
    }

    /**
     * 根据用户所选的CPU类型  获得所用的可供选择的组装推荐
     *
     * @return
     */
    @RequestMapping(value = "/getChoice", method = RequestMethod.POST)
    public ResponseObject<List<RentalComputer>> getChoiceComputer(RentalComputer com) {
        if (com.getCpuId() == null) {
            return ResponseObject.fail(-2, "您还没有选择CPU型号");
        }
        List<RentalComputer> result = rentalService.getChoiceComputer(com);
        return ResponseObject.successStautsData(result);
    }

    /**
     * 选择完配件，组装电脑（将用户所选配件组合，生成价格信息）,这里只能租，不能直接购买
     *
     * @return
     */
    @RequestMapping(value = "/getPrice", method = RequestMethod.POST)
    public ResponseObject<RentalComputer> getPriace(RentalComputer com, @RequestParam("buyNum") Integer buyNum) {
        if (com.getCpuId() == null && com.getMemoryId() == null && com.getCardId() == null && com.getDiskId() == null && com.getBoardId() == null && com.getPowerId() == null && com.getCaseId() == null && com.getRadiatorId() == null) {
            return ResponseObject.fail(-2, "您没有选择任何配件");
        }
        if (com.getCpuId() == null || com.getMemoryId() == null || com.getCardId() == null || com.getDiskId() == null || com.getBoardId() == null || com.getCaseId() == null || com.getPowerId() == null) {
            return ResponseObject.fail(-1, "由于您选择的配件不足以组装一台电脑，不支持租赁，请问您是需要购买这些配件吗？");
        }
        RentalComputer result = rentalService.getPrice(com, buyNum);
        return ResponseObject.successStautsData(result);
    }

    /**
     * 组装完成后，选择店铺，按用户选择的学校或者社区地址，若该学校或社区没有店铺，选择自营
     */
    @RequestMapping(value = "/getAllShop/{pageNum}/{pageSize}", method = RequestMethod.POST)
    public ResponseObject<PageInfo> getAllShop(@PathVariable(value = "pageNum") Integer pageNum,
                                               @PathVariable(value = "pageSize") Integer pageSize,
                                               HttpServletRequest request) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        PageHelper.startPage(pageNum, pageSize);
        List<RentalShop> list = rentalService.getAllRentalShop(userDto);
        PageInfo pageInfo = new PageInfo(list);
        return ResponseObject.successStautsData(pageInfo);
    }

    /**
     * 生成临时租赁订单
     *
     * @return
     */
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public ResponseObject<RentalOrder> creatOrder(RentalComputer com,HttpServletRequest request, @RequestParam("shopId") Integer shopId, @RequestParam("rentalMounth") Integer rentalMounth, @RequestParam("buyNum") Integer buyNum) {
        if (com.getCpuId() == null || com.getMemoryId() == null || com.getCardId() == null || com.getDiskId() == null || com.getBoardId() == null || com.getCaseId() == null || com.getPowerId() == null) {
            return ResponseObject.fail(-1, "由于您选择的配件不足以组装一台电脑，不支持租赁");
        }
//		rentalService.creatOrder(com);
        String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        return rentalService.creatOrder(com, shopId, rentalMounth, buyNum, userDto.getId());
    }

    /**
     * 购买接口组件（如果选择的配件不足以组装一台电脑，只能购买）
     *
     * @return
     */
    @RequestMapping(value = "/buyComputer", method = RequestMethod.POST)
    public ResponseObject<RentalOrder> buyComputer(RentalComputer com,HttpServletRequest request, @RequestParam("shopId") Integer shopId, @RequestParam("buyNum") Integer buyNum) {
        if (com.getCpuId() == null && com.getMemoryId() == null && com.getCardId() == null && com.getDiskId() == null && com.getBoardId() == null && com.getPowerId() == null && com.getCaseId() == null && com.getRadiatorId() == null) {
            return ResponseObject.fail(-2, "您没有选择任何配件");
        }
        String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        if (userDto == null) {
            return ResponseObject.fail(-3, "您尚未登录，请登录！");
        } else {
            return rentalService.buyComputer(com, userDto, shopId, buyNum);
        }
    }

    /**
     * 根据商铺ID拿到该商品下所有的临时订单
     *
     * @return
     */
    @RequestMapping(value = "/getOrderByShopId/{shopId}", method = RequestMethod.GET)
    public ResponseObject<List<RentalOrder>> getOrderByShopId(@PathVariable("shopId") Integer shopId) {
        return rentalService.getOrderByShopId(shopId);
    }

    
}
