package com.labelwall.mall.service.impl;

import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dao.RentalMapper;
import com.labelwall.mall.dao.SchoolMapper;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.entity.*;
import com.labelwall.mall.service.IRentalService;
import com.labelwall.util.BigDecimalUtil;
import com.labelwall.util.OrderUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service("rentalServiceImpl")
public class RentalServiceImpl implements IRentalService {
	@Autowired
	private RentalMapper rentalDao;
    @Autowired
    private SchoolMapper schoolMapper;
    
    
	@Override
	public List<RentalType> getAllType() {
		return rentalDao.getAllType();
	}
	@Override
	public int openShop(RentalShop shop, UserDto user) {
		shop.setUserId(user.getUserId());
		shop.setCreateTime(new Date());
		shop.setUpdateTime(new Date());
		shop.setStatus(1);
		shop.setLevel(1);
		if(shop.getLocationType()==0){
			shop.setLocationName("校园");
			String schoolName=schoolMapper.selectByPrimaryKey(shop.getSchoolId()).getName();
			shop.setSchoolName(schoolName);
			shop.setProvinceId(user.getProvinceId());
			shop.setCityId(user.getCityId());
			shop.setCountyId(user.getCountyId());
		}else{
			shop.setLocationName("社区");
		}
		//将商铺插入wall_rental_shop表
		int result= rentalDao.insert(shop);
		return result;
	}
	@Override
	public List<RentalCPU> getAllCPU(RentalCPU cpu) {
		return rentalDao.getAllCPU(cpu);
	}
	@Override
	public List<RentalMemory> getAllmemory(RentalMemory me) {
		return rentalDao.getAllmemory(me);
	}
	@Override
	public List<RentalCard> getAllcard(RentalCard me) {
		return rentalDao.getAllcard(me);
	}
	@Override
	public List<RentalDisk> getAlldisk(RentalDisk me) {
		return rentalDao.getAlldisk(me);
	}
	@Override
	public List<RentalBoard> getAllboard(RentalBoard me) {
		return rentalDao.getAllboard(me);
	}
	@Override
	public List<RentalPower> getAllpower(RentalPower me) {
		return rentalDao.getAllpower(me);
	}
	@Override
	public List<RentalCase> getAllcase(RentalCase me) {
		return rentalDao.getAllcase(me);
	}
	@Override
	public List<RentalRadiator> getAllradiator(RentalRadiator me) {
		return rentalDao.getAllradiator(me);
	}
	@Override
	public RentalComputer getPrice(RentalComputer computer,Integer buyNum) {
//		RentalCPU cpu=rentalDao.getCPUById(computer.getCpuId());
//		computer.setCpu(cpu);
//		RentalMemory memo=rentalDao.getMemoryById(computer.getMemoryId());
//		computer.setMemo(memo);
//		RentalCard card=rentalDao.getCardById(computer.getCardId());
//		computer.setCard(card);
//		RentalDisk disk=rentalDao.getDiskById(computer.getDiskId());
//		computer.setDisk(disk);
//		RentalBoard board=rentalDao.getBoardById(computer.getBoardId());
//		computer.setBoard(board);
//		RentalPower power=rentalDao.getPowerById(computer.getPowerId());
//		computer.setPower(power);
//		RentalCase cas=rentalDao.getCaseById(computer.getCaseId());
//		computer.setCas(cas);
//		RentalRadiator radiator=rentalDao.getRadiatorById(computer.getRadiatorId());
//		computer.setRadiator(radiator);
//		double[] arms={cpu.getCpuPrice().doubleValue(),memo.getMemoryPrice().doubleValue(),card.getCardPrice().doubleValue(),disk.getDiskPrice().doubleValue(),
//				board.getBoardPrice().doubleValue(),power.getPowerPrice().doubleValue(),cas.getCasePrice().doubleValue(),radiator.getRadiatorPrice().doubleValue()};
//		BigDecimal unit_price=BigDecimalUtil.adds(arms);
		
		
		BigDecimal totolPrice=BigDecimal.valueOf(0);
		if(computer.getCpuId()!=null){
			RentalCPU cpu=rentalDao.getCPUById(computer.getCpuId());
			computer.setCpu(cpu);
			totolPrice= BigDecimalUtil.add(cpu.getCpuPrice().doubleValue(), totolPrice.doubleValue());
		}
		if(computer.getMemoryId()!=null){
			RentalMemory memo=rentalDao.getMemoryById(computer.getMemoryId());
			computer.setMemo(memo);
			totolPrice= BigDecimalUtil.add(memo.getMemoryPrice().doubleValue(), totolPrice.doubleValue());
		}
		if(computer.getCardId()!=null){
			RentalCard card=rentalDao.getCardById(computer.getCardId());
			computer.setCard(card);
			totolPrice= BigDecimalUtil.add(card.getCardPrice().doubleValue(), totolPrice.doubleValue());
		}
		if(computer.getDiskId()!=null){
			RentalDisk disk=rentalDao.getDiskById(computer.getDiskId());
			computer.setDisk(disk);
			totolPrice= BigDecimalUtil.add(disk.getDiskPrice().doubleValue(), totolPrice.doubleValue());
		}
		if(computer.getBoardId()!=null){
			RentalBoard board=rentalDao.getBoardById(computer.getBoardId());
			computer.setBoard(board);
			totolPrice= BigDecimalUtil.add(board.getBoardPrice().doubleValue(), totolPrice.doubleValue());
		}
		if(computer.getPowerId()!=null){
			RentalPower power=rentalDao.getPowerById(computer.getPowerId());
			computer.setPower(power);
			totolPrice= BigDecimalUtil.add(power.getPowerPrice().doubleValue(), totolPrice.doubleValue());
		}
		if(computer.getCaseId()!=null){
			RentalCase cas=rentalDao.getCaseById(computer.getCaseId());
			computer.setCas(cas);
			totolPrice= BigDecimalUtil.add(cas.getCasePrice().doubleValue(), totolPrice.doubleValue());
		}
		if(computer.getRadiatorId()!=null){
			RentalRadiator radiator=rentalDao.getRadiatorById(computer.getRadiatorId());
			computer.setRadiator(radiator);
			totolPrice= BigDecimalUtil.add(radiator.getRadiatorPrice().doubleValue(), totolPrice.doubleValue());
		}
		if(computer.getCDRom()!=null){
			computer.setCDRom(1);
			totolPrice= BigDecimalUtil.add(200, totolPrice.doubleValue());
		}
		if(buyNum==null){
			buyNum=1;
		}
//		//单价乘以购买数量
//		BigDecimal price=BigDecimalUtil.multiply(totolPrice.doubleValue(), buyNum);
		//购买电脑价格计算
		computer.setPrice_buy(totolPrice);
		//租电脑分期价格计算
		computer.setPrice_6(BigDecimalUtil.divide(totolPrice.doubleValue(), 6));
		computer.setPrice_12(BigDecimalUtil.divide(totolPrice.doubleValue(), 12));
		computer.setPrice_24(BigDecimalUtil.divide(totolPrice.doubleValue(), 24));
		return computer;
	}

	
	
	@Override
	public List<RentalComputer> getChoiceComputer(RentalComputer com) {
		List<RentalComputer> result=rentalDao.getChoiceComputer(com.getCpuId());
		for (RentalComputer rentalComputer : result) {
			RentalCPU cpu=rentalDao.getCPUById(rentalComputer.getCpuId());
			rentalComputer.setCpu(cpu);
			RentalBoard board=rentalDao.getBoardById(rentalComputer.getBoardId());
			rentalComputer.setBoard(board);
			RentalCase cas=rentalDao.getCaseById(rentalComputer.getCaseId());
			rentalComputer.setCas(cas);
			RentalMemory memo=rentalDao.getMemoryById(rentalComputer.getMemoryId());
			rentalComputer.setMemo(memo);
		}
		return result;
	}
	@Override
	public List<RentalShop> getAllRentalShop(UserDto user) {
		if(user==null){
			return rentalDao.getAllRentalShop(null,null);
		}else{
			return rentalDao.getAllRentalShop(user.getCountyId(),user.getSchoolId());
		}
		
	}
	@Override
	public ResponseObject<RentalOrder> creatOrder(RentalComputer com, Integer shopId, Integer rentalMounth, Integer buyNum, Integer userId) {
		if(rentalMounth==null){
			return ResponseObject.fail(-2, "请选择正确的分期月份");
		}
		if(shopId==null){
			return ResponseObject.fail(-3, "请选择下单的商铺");
		}
		if(buyNum==null){
			return ResponseObject.fail(-4, "请选择购买数量");
		}
		
		RentalComputer tmp=getPrice(com,buyNum);
		if(com.getCDRom()==null){
			tmp.setCDRom(0);
		}
		RentalOrder order =new RentalOrder();
		BeanUtils.copyProperties(tmp, order);
		switch (rentalMounth) {
		case 6:
			order.setUnitPrice(tmp.getPrice_6());
			order.setQuantity(buyNum);
			order.setTotalPrice(BigDecimalUtil.multiply(tmp.getPrice_6().doubleValue(), buyNum));
			break;
		case 12:
			order.setUnitPrice(tmp.getPrice_12());
			order.setQuantity(buyNum);
			order.setTotalPrice(BigDecimalUtil.multiply(tmp.getPrice_12().doubleValue(), buyNum));
			break;
		case 24:
			order.setUnitPrice(tmp.getPrice_24());
			order.setQuantity(buyNum);
			order.setTotalPrice(BigDecimalUtil.multiply(tmp.getPrice_24().doubleValue(), buyNum));
			break;
		default:
			return ResponseObject.fail(-2, "请选择正确的分期月份");
		}
		order.setCreateTime(new Date());
		order.setUpdateTime(new Date());
		order.setUserId(userId);
		order.setShopId(shopId);
		Integer countNum=rentalDao.getOrderCount();
		String orderName=tmp.getCpu().getCupType()+"-"+tmp.getCpu().getCpuGeneration()+"-"+tmp.getCpu().getCpuNum()+"-"+tmp.getBoard().getBoardName()+"-"+tmp.getCard().getCardName()
				+"-"+tmp.getCas().getCaseName()+"-"+tmp.getDisk().getDiskName()+"-"+tmp.getMemo().getMemoryName()+"-"+tmp.getPower().getPowerName()+"-"+tmp.getRadiator().getRadiatorName();
		if(tmp.getCDRom()==1){
			orderName+="-光驱";
		}
		order.setOrderName(rentalMounth+"个月分期电脑主机(包括："+orderName+")");
		order.setOrderNum(OrderUtils.getOrderNum(countNum));
		order.setOrderType(0);
		int result=rentalDao.insertRentalOrder(order);
		if(result>0){
			return ResponseObject.successStautsData(order);
		}else{
			return ResponseObject.fail(-5, "订单生成失败，请重新购买");
		}
	}
	@Override
	public ResponseObject<RentalOrder> buyComputer(RentalComputer com, UserDto user, Integer shopId, Integer buyNum) {
		if(buyNum==null){
			return ResponseObject.fail(-1, "请选择购买数量");
		}
		if(shopId==null){
			return ResponseObject.fail(-3, "请选择下单的商铺");
		}
		RentalComputer tmp=getPrice(com,buyNum);
		if(com.getCDRom()==null){
			tmp.setCDRom(0);
		}
		RentalOrder order =new RentalOrder();
		BeanUtils.copyProperties(tmp, order);
		order.setUnitPrice(tmp.getPrice_buy());
		order.setQuantity(buyNum);
		order.setTotalPrice(BigDecimalUtil.multiply(tmp.getPrice_buy().doubleValue(), buyNum));
		order.setOrderName("购买电脑配件");
		order.setCreateTime(new Date());
		order.setUpdateTime(new Date());
		order.setShopId(shopId);
		order.setUserId(user.getUserId());
		Integer countNum=rentalDao.getOrderCount();
		order.setOrderNum(OrderUtils.getOrderNum(countNum));
		order.setOrderType(1);
		int result=rentalDao.insertRentalOrder(order);
		if(result>0){
			return ResponseObject.successStautsData(order);
		}else{
			return ResponseObject.fail(-5, "订单生成失败，请重新购买");
		}
	}
	@Override
	public ResponseObject<List<RentalOrder>> getOrderByShopId(Integer shopId) {
		List<RentalOrder> result=rentalDao.getOrderByShopId(shopId);
		return ResponseObject.successStautsData(result);
	}
	
}
