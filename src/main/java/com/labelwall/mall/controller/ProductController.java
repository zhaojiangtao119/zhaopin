package com.labelwall.mall.controller;

import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.dto.ProductCategoryDto;
import com.labelwall.mall.service.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2017-12-04.
 */
@RestController
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    private IProductCategoryService productCategoryService;

    @RequestMapping(value = "get_category", method = RequestMethod.GET)
    public ResponseObject<List<ProductCategoryDto>>
    getCategory(@RequestParam(value = "categoryId", required = false) Integer categoryId) {
        return productCategoryService.getCategoryList(categoryId);
    }

    @RequestMapping(value = "get_category_id", method = RequestMethod.GET)
    public ResponseObject<List<Integer>>
    getCategoryByCategoryId(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        return productCategoryService.getCategoryAndChildrenByCategoryId(categoryId);
    }
}
