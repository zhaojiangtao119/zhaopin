package com.labelwall.mall.dto;

import com.labelwall.mall.entity.ShopServices;
import org.springframework.web.multipart.MultipartFile;

public class ShopServicesDto extends ShopServices {
	private MultipartFile serviceImg;

	public MultipartFile getServiceImg() {
		return serviceImg;
	}

	public void setServiceImg(MultipartFile serviceImg) {
		this.serviceImg = serviceImg;
	}
	
	
}
