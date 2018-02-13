package com.labelwall.mall.service;

import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.entity.*;

import java.util.List;

public interface IRentalService {

	List<RentalType> getAllType();

	int openShop(RentalShop shop, UserDto user);

	List<RentalCPU> getAllCPU(RentalCPU cpu);

	List<RentalMemory> getAllmemory(RentalMemory me);

	List<RentalCard> getAllcard(RentalCard me);

	List<RentalDisk> getAlldisk(RentalDisk me);

	List<RentalBoard> getAllboard(RentalBoard me);

	List<RentalPower> getAllpower(RentalPower me);

	List<RentalCase> getAllcase(RentalCase me);

	List<RentalRadiator> getAllradiator(RentalRadiator me);

	RentalComputer getPrice(RentalComputer computer, Integer buyNum);

//	ResponseObject<String> buyComputer(RentalComputer com);

	List<RentalComputer> getChoiceComputer(RentalComputer com);

	List<RentalShop> getAllRentalShop(UserDto user);

	ResponseObject<RentalOrder> creatOrder(RentalComputer com, Integer shopId, Integer rentalMounth, Integer buyNum, Integer userId);

	ResponseObject<RentalOrder> buyComputer(RentalComputer com, UserDto user, Integer shopId, Integer buyNum);

	ResponseObject<List<RentalOrder>> getOrderByShopId(Integer shopId);


}
