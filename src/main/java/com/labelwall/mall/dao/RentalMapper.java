package com.labelwall.mall.dao;

import com.labelwall.mall.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RentalMapper {

	List<RentalType> getAllType();

	int insert(RentalShop shop);

	List<RentalCPU> getAllCPU(RentalCPU cpu);

	List<RentalMemory> getAllmemory(RentalMemory me);

	List<RentalCard> getAllcard(RentalCard me);

	List<RentalDisk> getAlldisk(RentalDisk me);

	List<RentalBoard> getAllboard(RentalBoard me);

	List<RentalPower> getAllpower(RentalPower me);

	List<RentalCase> getAllcase(RentalCase me);

	List<RentalRadiator> getAllradiator(RentalRadiator me);
//根据ID获取组件
	RentalCPU getCPUById(Integer integer);

	RentalMemory getMemoryById(Integer memoryId);

	RentalCard getCardById(Integer cardId);

	RentalDisk getDiskById(Integer diskId);

	RentalBoard getBoardById(Integer boardId);


	RentalPower getPowerById(Integer powerId);

	RentalRadiator getRadiatorById(Integer radiatorId);

	RentalCase getCaseById(Integer caseId);

	List<RentalComputer> getChoiceComputer(Integer cpuId);

	List<RentalShop> getAllRentalShop(@Param("countyId") Integer countryId, @Param("schoolId") Integer schoolId);

	Integer getOrderCount();

	int insertRentalOrder(RentalOrder order);

	List<RentalOrder> getOrderByShopId(Integer shopId);

}
