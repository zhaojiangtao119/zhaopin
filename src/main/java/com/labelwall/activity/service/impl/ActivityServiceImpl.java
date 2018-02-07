package com.labelwall.activity.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.labelwall.activity.dao.ActivityMapper;
import com.labelwall.activity.dto.ActivityDto;
import com.labelwall.activity.entity.ActivityInfo;
import com.labelwall.activity.service.IActivityService;
import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.mall.entity.User;
import com.labelwall.mall.service.IUserService;
import com.labelwall.util.storage.QiniuStorage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@SuppressWarnings("all")
public class ActivityServiceImpl implements IActivityService {

    @Autowired
    private ActivityMapper activityDao;
    @Autowired
    private IUserService userService;
    /*@Autowired
    private LoginDao loginDao;

	public List<ActivityInfo> selectByStartUser(User user) {
		return activityDao.selectByStartUser(user);
	}

	@Transactional
	public int add(User user, ActivityInfo info, ActivityPayInfo order) {

		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		info.setCreattime(dt.format(new Date()));
		if (info.getFree() == null || info.getFree() == 0) {
			info.setFree(0);
			info.setAmount(new BigDecimal("0"));
		}
		info.setStatus(0);
		// ������Ϣ
		activityDao.insertActivity(info);
		// ActivityInfo select = activityDao.selectByInfo(info,user.getId());
		// �޸Ļ����״̬��֧��
		activityDao.updateOrder(order.getWIDout_trade_no(), user.getId(), info.getId());

		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getId().toString());
		map.put("activityId", info.getId().toString());
		map.put("createTime", info.getCreattime());
		// ���뵱ǰ�û�������һ�����¼
		Integer result = activityDao.insertStartUser(map);
		return result;
	}

	// ���ѳɹ������
	@Transactional
	public int join(User user, ActivityInfo info, ActivityPayInfo order) {
		activityDao.updateOrder(order.getWIDout_trade_no(), user.getId(), info.getId());
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getId().toString());
		map.put("activityId", info.getId().toString());
		map.put("createTime", info.getCreattime());
		Integer result = activityDao.insertJoinUser(map);
		return result;
	}

	public Integer startActivity(String date) {
		return activityDao.startActivity(date);
	}

	public void updateTaskTim(int id, String date) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("date", date);
		activityDao.updateTaskTim(map);
	}

	public int endActivity(String date) {
		return activityDao.endActivity(date);
	}

	public int deleteActivity(String date) {

		return activityDao.deleteActivity(date);
	}

	public ActivityDto selectById(ActivityInfo tmp) {
		ActivityInfo activityByselect = activityDao.select(tmp);
		ActivityDto dto = new ActivityDto();
		BeanUtils.copyProperties(activityByselect, dto);
		UserInfo startUser = loginDao.selectStartUser(activityByselect.getId());
		dto.setStartUser(startUser);// ���û�����ߵ���Ϣ

		List<UserInfo> list = loginDao.selectJoinUser(activityByselect.getId());
		dto.setJoinUser(list);
		return dto;
	}

	// ��Ѽ���
	@Transactional
	public Integer joinActivity(User user, Integer activity_id, ActivityPayInfo order) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (order != null) {
			activityDao.updateOrder(order.getWIDout_trade_no(), user.getId(), activity_id);
		}
		map.put("user_id", user.getId());
		map.put("activity_id", activity_id);
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		map.put("createTime", dt.format(new Date()));
		return activityDao.joinActivity(map);
	}

	public ActivityListDto selectByTheme(ActivityInfo tmp) {

		return null;
	}

	*/

    /***
     * �����û���Ϣ��ѯ���л���飬usertype 0 Ϊ������ 1Ϊ������
     *//*
    // public List<ActivityDto> selectByUser(int userType, User user) {
	// List<ActivityInfo> info=activityDao.selectByUser(userType,user);
	// List<ActivityDto> dto=new ArrayList<ActivityDto>();
	// for (ActivityInfo activityInfo : info) {
	// ActivityDto selectById = selectById(activityInfo);
	// dto.add(selectById);
	// }
	// return dto;
	// }

	public void deleteUser(String activity_id, String user_id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("activity_id", activity_id);
		map.put("user_id", user_id);
		activityDao.deleteUser(map);
	}

	// public List<UserInfo> updateJoinNum(User user) {
	// List<UserInfo> data=activityDao.selectJoinUser(user);
	// return data;
	// }----1.24

	public Page<ActivityInfo> selectPager(ActivityInfo info, Page<ActivityInfo> page, User user) {
		Page<ActivityInfo> result = new Page<ActivityInfo>();

		int pagerNumber = result.getPageNumber();
		int tog = (page.getCurrentPage() - 1) * pagerNumber;

		Map map = new HashMap();
		map.put("tog", tog);
		map.put("pagerNumber", pagerNumber);
		map.put("info", info);
		map.put("key", page.getKeyword());
		map.put("totleTime", page.getTotleTime());
		map.put("schoolId", page.getSchoolId());
		map.put("type", page.getType());
		map.put("city", page.getCity());
		map.put("country", page.getCountry());
		// map.put("orderBy", page.orderBy());
		// ��ȡ��ǰ�û������Ļ���û�����Ļ
		List<ActivityInfo> byStartUser = activityDao.selectByStartUser(user);
		List<ActivityInfo> byjoinUser = activityDao.selectByjoinUser(user);
		List<ActivityInfo> datas = activityDao.selectLikeByPage(map);
		// TODO list����removeԪ�ص�����
		for (int i = 0; i < datas.size(); i++) {
			for (ActivityInfo activityInfo : byStartUser) {
				if (datas.get(i).getId() == activityInfo.getId()) {
					datas.remove(i);
				}
			}
		}
		for (int i = 0; i < datas.size(); i++) {
			for (ActivityInfo activityInfo : byjoinUser) {
				if (datas.get(i).getId() == activityInfo.getId()) {
					datas.remove(i);
				}
			}
		}
		int count = activityDao.selectCountByPage(map);
		result.setTotalNumber(count);
		result.setDatas(datas);
		result.setKeyword(page.getKeyword());
		return result;
	}

	@Transactional
	public int addOrder(ActivityInfo info, ActivityPayInfo order, Integer userId) {
		// 1�쳣 2���� 3�ɹ�
		int result_add = activityDao.addOrder(info, order, userId);
		if (result_add > 0) {
			// ����������ɳɹ����۳���ң��޸��û��˻�
			int result_update = activityDao.cutAccount(userId, order.getWIDtotal_fee());
			if (result_update > 0) {// �����ҿ۳��ɹ�
				// orderType��0����1����
				if (order.getType() == 1) {// ����Ǽ����ĸ���������߼���Ľ�Ҵ��뷢�����˻�
					ActivityDto selectById = selectById(info);
					Integer startUserId = selectById.getStartUser().getId();
					int addAccount = activityDao.addAccount(startUserId, order.getWIDtotal_fee());
					if (addAccount < 0) {
						return 1;
					} else {
						AccountTradeInfo addTrade = new AccountTradeInfo();
						Long account_id = activityDao.selectAccountByuser(userId);
						addTrade.setAccount_id(account_id);
						addTrade.setOrder_id((long) 0);
						addTrade.setTrade_type(1);
						addTrade.setTrade_num(order.getWIDtotal_fee());
						addTrade.setUser_id(userId);
						SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						addTrade.setCrate_time(dt.format(new Date()));
						int addT = activityDao.insertTradeHistory(addTrade);
						if (addT < 0) {
							return 1;
						}
					}
				}

				// ����ôν��׼�¼��������ʷ��¼
				AccountTradeInfo trade = new AccountTradeInfo();
				// ��ȡ��ǰ�û��˻���id
				Long account_id = activityDao.selectAccountByuser(userId);
				// ��ȡ��ǰ���id
				Long orderByselect = activityDao.selectOrder(order);

				trade.setAccount_id(account_id);
				trade.setOrder_id(orderByselect);
				trade.setTrade_num(order.getWIDtotal_fee());
				trade.setTrade_type(-1);
				trade.setUser_id(userId);
				SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				trade.setCrate_time(dt.format(new Date()));
				int result_insert = activityDao.insertTradeHistory(trade);
				if (result_insert > 0) {
					return 3;
				} else {
					return 1;
				}
			} else {
				return 2;
			}
		} else {
			return 1;
		}
	}

	public Integer selectCountOrders() {
		return activityDao.selectCountOrders();
	}

	public List<ActivityInfo> selectByjoinUser(User user) {
		return activityDao.selectByjoinUser(user);
	}

	public List<ActivityInfo> selectNearJoin(User user) {
		return activityDao.selectNearJoin(user);
	}

	public void insertActivity(ActivityInfo info) {
		activityDao.insertActivity(info);
	}

	public List<UserInfo> getJoinUserNoCheck(ActivityInfo info) {
		return activityDao.getJoinUserNoCheck(info.getId());
	}

	public int agreeJoin(ActivityInfo activity, UserInfo user) {
		return activityDao.agreeJoin(activity.getId(), user.getId());
	}

	public List<String> getAllTypes() {
		return activityDao.getAllTypes();
	}

	public List<String> getAllStyles() {
		return activityDao.getAllStyles();
	}

	public List<City> getAllCity() {
		return activityDao.getAllCity();
	}

	public List<School> getSchoolByCity(Integer province_id) {
		return activityDao.getSchoolByCity(province_id);
	}

	public List<ActivityInfo> selectByhasjoin(User user) {
		return activityDao.selectHasJoin(user);
	}*/

    // -------------------------------------------------------------------
    @Override
    public ResponseObject<PageInfo> query(Integer userId, Integer pageNum, Integer pageSize, ActivityDto activity) {
        PageHelper.startPage(pageNum, pageSize);
        List<ActivityInfo> activityInfos = activityDao.selectActivityByPage(activity,null);
        if (userId != null) {
            List<ActivityInfo> byStartUser = activityDao.selectByStartUserId(userId);
            List<ActivityInfo> byjoinUser = activityDao.selectByjoinUserId(userId);
            List<Integer> activityIds = new ArrayList<>();
            for (ActivityInfo activityInfo : byStartUser) {
                activityIds.add(activityInfo.getId());
            }
            for (ActivityInfo activityInfo : byjoinUser) {
                activityIds.add(activityInfo.getId());
            }
            PageHelper.startPage(pageNum, pageSize);
            activityInfos = activityDao.selectActivityByPage(activity,activityIds);
            /*int size = byStartUser.size();
            for (int i = 0; i < size; i++) {
                for (Iterator<ActivityInfo> iter = activityInfos.iterator(); iter.hasNext(); ) {
                    if (iter.next().getId() == byStartUser.get(i).getId()) {
                        iter.remove();
                    }
                }
            }
            size = byjoinUser.size();
            for (int i = 0; i < size; i++) {
                for (Iterator<ActivityInfo> iter = activityInfos.iterator(); iter.hasNext(); ) {
                    if (iter.next().getId() == byjoinUser.get(i).getId()) {
                        iter.remove();
                    }
                }
            }*/
        }
        PageInfo pageInfo = new PageInfo(activityInfos);
        return ResponseObject.successStautsData(pageInfo);
    }

    @Override
    public ResponseObject<ActivityDto> getActivityInfo(Integer activityId) {
        if (activityId == null) {
            ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(), ResponseStatus.ERROR_PARAM.getValue());
        }
        ActivityDto activityDto = new ActivityDto();
        //1.活动的信息
        ActivityInfo activityInfo = activityDao.selectByPrimaryKey(activityId);
        if (activityInfo != null) {
            BeanUtils.copyProperties(activityInfo, activityDto);
        }
        //2.活动创建者的信息
        User user = userService.selectByActivityId(activityInfo.getId());
        if (StringUtils.isBlank(user.getHead())) {
            user.setHead(QiniuStorage.getUserHeadUrl(Const.DEFAULT_USER_HEAD));
        } else {
            user.setHead(QiniuStorage.getUserHeadUrl(user.getHead()));
        }
        activityDto.setStartUser(user);
        //3.已近加入的用户
        List<Integer> joinUserIds = activityDao.selectIdsByActivityId(activityInfo.getId(), 1);
        if (CollectionUtils.isNotEmpty(joinUserIds)) {
            List<User> joinUsers = userService.selectByUserIds(joinUserIds);
            for (User joinUser : joinUsers) {
                if (StringUtils.isBlank(joinUser.getHead())) {
                    joinUser.setHead(QiniuStorage.getUserHeadUrl(Const.DEFAULT_USER_HEAD));
                } else {
                    joinUser.setHead(QiniuStorage.getUserHeadUrl(joinUser.getHead()));
                }
            }
            activityDto.setJoinUser(joinUsers);
        }
        //4.待审核加入的用户
        List<Integer> noChackedJoinUserIds = activityDao.selectIdsByActivityId(activityInfo.getId(), 0);
        if (CollectionUtils.isNotEmpty(noChackedJoinUserIds)) {
            List<User> noChackedJoinUsers = userService.selectByUserIds(joinUserIds);
            for (User noCheckedJoinUser : noChackedJoinUsers) {
                if (StringUtils.isBlank(noCheckedJoinUser.getHead())) {
                    noCheckedJoinUser.setHead(QiniuStorage.getUserHeadUrl(Const.DEFAULT_USER_HEAD));
                } else {
                    noCheckedJoinUser.setHead(QiniuStorage.getUserHeadUrl(noCheckedJoinUser.getHead()));
                }
            }
            activityDto.setNoCheckedJoinUser(noChackedJoinUsers);
        }
        return ResponseObject.successStautsData(activityDto);
    }

    @Override
    public ResponseObject<PageInfo> getActivityUserStart(Integer userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ActivityInfo> activityInfoList = activityDao.selectUserStartByUserId(userId);
        PageInfo pageInfo = new PageInfo(activityInfoList);
        return ResponseObject.successStautsData(pageInfo);
    }

    @Override
    public ResponseObject<PageInfo> getActivityUserJoin(Integer userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ActivityInfo> activityInfoList = activityDao.selectUserJoinByUserId(userId);
        PageInfo pageInfo = new PageInfo(activityInfoList);
        return ResponseObject.successStautsData(pageInfo);
    }
}
