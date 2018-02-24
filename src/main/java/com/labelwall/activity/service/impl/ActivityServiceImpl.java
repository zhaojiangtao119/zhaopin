package com.labelwall.activity.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.labelwall.activity.dao.ActivityMapper;
import com.labelwall.activity.dto.ActivityDto;
import com.labelwall.activity.entity.ActivityInfo;
import com.labelwall.activity.entity.ActivityJoin;
import com.labelwall.activity.entity.ActivityStyles;
import com.labelwall.activity.entity.ActivityTypes;
import com.labelwall.activity.service.IActivityService;
import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.entity.User;
import com.labelwall.mall.service.ISchoolService;
import com.labelwall.mall.service.IUserService;
import com.labelwall.util.DateTimeUtil;
import com.labelwall.util.storage.QiniuStorage;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTimeUtils;
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
    @Autowired
    private ISchoolService schoolService;
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

        //TODO 获取活动列表之前，是否先判断修改活动的报名状态
        //1.获取所有符合活动状态的活动集合
        List<ActivityInfo> activityInfosList = activityDao.selectActivityAll();
        //2.当前的时间
        Date currentDate = new Date();
        for (ActivityInfo activityInfo : activityInfosList) {
            //3.判断活动的报名时间与当前时间大小
            String startTimeStr = activityInfo.getStarttime();
            Date startTime = DateTimeUtil.strToDate(startTimeStr);
            String endTimeStr = activityInfo.getEndtime();
            Date endTime = DateTimeUtil.strToDate(endTimeStr);
            if (currentDate.compareTo(startTime) > 0 && currentDate.compareTo(endTime) < 0) {
                //活动报名进行中
                activityDao.updateActivityStatus(activityInfo.getId(), 1);
            }
            if (currentDate.compareTo(endTime) > 0) {
                //活动报名结束
                activityDao.updateActivityStatus(activityInfo.getId(), 2);
            }
        }

        PageHelper.startPage(pageNum, pageSize);
        List<ActivityInfo> activityInfos = activityDao.selectActivityByPage(activity, null);
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
            activityInfos = activityDao.selectActivityByPage(activity, activityIds);
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
            List<User> noChackedJoinUsers = userService.selectByUserIds(noChackedJoinUserIds);
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

    @Override
    public ResponseObject modifyAgreeUserJoin(Integer startUserId, Integer activityId, Integer joinUserId) {
        ActivityInfo activity = new ActivityInfo();
        activity.setId(activityId);
        User user = new User();
        user.setId(joinUserId);
        ActivityInfo activityInfo = activityDao.selectByPrimaryKey(activityId);
        int count = activityDao.getActivityUserNum(activityId);
        if (activityInfo.getNum_limt() <= count) {
            //活动人数已满
            return ResponseObject.failStatusMessage("活动人数已满");
        }
        int result = activityDao.agreeJoin(activityId, startUserId, joinUserId);
        if (result > 0) {
            return ResponseObject.successStatus();
        } else {
            return ResponseObject.failStatusMessage("加入失败");
        }
    }

    @Override
    public ResponseObject saveJoinActivity(Integer activityId, Integer userId) {
        //加入活动，先决条件：0.判断当前用户的信息是否完善 1.判断该活动人数限制，2.判断用户相关活动时间是否冲突， 3.判断是免费还是付费
        ResponseObject validateUserInfo = validateUserInfo(userId);
        if (!validateUserInfo.isSuccess()) {
            return validateUserInfo;
        }
        //TODO 活动参加的人数
        ActivityInfo activityInfo = activityDao.selectByPrimaryKey(activityId);
        int count = activityDao.getActivityUserNum(activityId);
        if (activityInfo.getNum_limt() <= count) {
            //活动人数已满
            return ResponseObject.failStatusMessage("活动人数已满");
        }
        //判断用户的时间是否与当前活动时间冲突
        ResponseObject validateTime = vaildateUserTime(activityInfo, userId);
        if (!validateTime.isSuccess()) {
            return validateTime;
        }
        //判断活动是否收费
        if (activityInfo.getFree() == 1) {//收费
            //TODO 通知前台跳转
        } else if (activityInfo.getFree() == 0) {//免费
            //直接申请加入活动
            ActivityJoin activityJoin = new ActivityJoin();
            activityJoin.setUserId(userId);
            activityJoin.setActivityId(activityId);
            int rowCount = activityDao.saveJoinActivity(activityJoin);
            if (rowCount > 0) {
                return ResponseObject.successStatus();
            } else {
                return ResponseObject.failStatusMessage("加入失败");

            }
        }
        return null;
    }

    private ResponseObject validateUserInfo(Integer userId) {
        ResponseObject<UserDto> userDtoResponse = userService.selectByUserId(userId);
        UserDto userDto = userDtoResponse.getData();
        if (userDto != null) {
            if (StringUtils.isBlank(userDto.getEmail())) {
                return ResponseObject.failStatusMessage("请完善您的邮箱信息，方便联系");
            }
        }
        return ResponseObject.successStatus();
    }

    private ResponseObject vaildateUserTime(ActivityInfo currentJoinActivity, Integer userId) {
        //获取当前用户发起的活动信息
        List<ActivityInfo> start = activityDao.selectUserStartByUserId(userId);
        Date infoStart = DateTimeUtil.strToDate(currentJoinActivity.getDetailStartTime());
        Date infoEnd = DateTimeUtil.strToDate(currentJoinActivity.getDetailEndTime());
        for (ActivityInfo activityDto : start) {
            Date dtoStart = DateTimeUtil.strToDate(activityDto.getDetailStartTime());
            Date dtoEnd = DateTimeUtil.strToDate(activityDto.getDetailEndTime());
            // 如果所报名活动的活动开始时间在用户发起活动的时间内,失败
            if (infoStart.compareTo(dtoStart) > 0 && infoStart.compareTo(dtoEnd) < 0) {
                return ResponseObject.failStatusMessage("请注意您的时间安排是否冲突");
                // 如果所报名活动的活动结束时间在用户已经发起活动的时间内，失败
            } else if (infoStart.compareTo(dtoStart) < 0 && infoEnd.compareTo(dtoStart) > 0) {
                return ResponseObject.failStatusMessage("请注意您的时间安排是否冲突");
            }
        }

        //获取当前用户加入的活动信息
        List<ActivityInfo> join = activityDao.selectUserJoinByUserId(userId);
        for (ActivityInfo activityDto : join) {
            Date dtoStart = DateTimeUtil.strToDate(activityDto.getDetailStartTime());
            Date dtoEnd = DateTimeUtil.strToDate(activityDto.getDetailEndTime());
            // 如果所报名活动的开始时间在用户已经参与活动的时间内,失败
            if (infoStart.compareTo(dtoStart) > 0 && infoStart.compareTo(dtoEnd) < 0) {
                return ResponseObject.failStatusMessage("请注意您的时间安排是否冲突");
                // 如果所报名活动的结束时间在用户已经参与活动的时间内，失败
            } else if (infoStart.compareTo(dtoStart) < 0 && infoEnd.compareTo(dtoStart) > 0) {
                return ResponseObject.failStatusMessage("请注意您的时间安排是否冲突");
            }
        }
        return ResponseObject.successStatus();
    }

    @Override
    public ResponseObject validateUserJoin(Integer userId, Integer activityId) {
        if (userId == null || activityId == null) {
            return ResponseObject.
                    fail(ResponseStatus.ERROR_PARAM.getCode(), ResponseStatus.ERROR_PARAM.getValue());
        }
        int count = activityDao.validateUserJoin(userId, activityId);
        if (count == 1) {
            return ResponseObject.successStatus();//参加了该活动
        }
        return ResponseObject.failStatusMessage("没有参加该活动");
    }

    @Override
    public ResponseObject quitActivity(Integer userId, Integer activityId) {
        if (userId == null || activityId == null) {
            return ResponseObject.
                    fail(ResponseStatus.ERROR_PARAM.getCode(), ResponseStatus.ERROR_PARAM.getValue());
        }
        int rowCount = activityDao.quitActivity(userId, activityId);
        if (rowCount > 0) {
            //退出成功
            return ResponseObject.successStatus();
        }
        return ResponseObject.failStatusMessage("退出失败");
    }

    @Override
    public ResponseObject<List<ActivityTypes>> getAllTypes() {
        List<ActivityTypes> activityTypesList = activityDao.getAllTypes();
        return ResponseObject.successStautsData(activityTypesList);
    }

    @Override
    public ResponseObject<List<ActivityStyles>> getAllStyles() {
        List<ActivityStyles> activityStylesList = activityDao.getAllStyles();
        return ResponseObject.successStautsData(activityStylesList);
    }

    @Override
    public ResponseObject<Integer> createFreeActivity(ActivityInfo activityInfo) {//免费活动的创建
        if (activityInfo == null) {
            ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        //1.验证输入的时间是否正确
        ResponseObject validateTime = validateActivityTime(activityInfo);
        if (!validateTime.isSuccess()) {
            return validateTime;
        }
        //2.验证当前用户的关联事件是否存在时间上的冲突
        ResponseObject validateUserTime = vaildateUserTime(activityInfo, activityInfo.getUserId());
        if (!validateUserTime.isSuccess()) {
            return validateUserTime;
        }
        //3.将数据插入到数据库
        //获取schoolId
        if (StringUtils.isNotBlank(activityInfo.getSchool()) &&
                StringUtils.isNotBlank(activityInfo.getLocation())) {
            Integer schoolId = schoolService.
                    getSchoolIdByProNameSchName(activityInfo.getLocation(), activityInfo.getSchool());
            activityInfo.setSchoolId(schoolId);
        }
        activityInfo.setAmount(new BigDecimal("0"));
        int rowCount = activityDao.createFreeActivity(activityInfo);
        if (rowCount > 0) {
            //创建成功
            //4.修改well_start_activity;
            activityDao.createStartActivity(activityInfo.getId(), activityInfo.getUserId());
            return ResponseObject.successStautsData(activityInfo.getId());
        }
        return ResponseObject.failStatusMessage("创建失败");
    }

    private ResponseObject validateActivityTime(ActivityInfo activityInfo) {
        //判断输入的时间格式是否正确
        if (!DateTimeUtil.isValiDateFormat(activityInfo.getStarttime()) &&
                !DateTimeUtil.isValiDateFormat(activityInfo.getEndtime()) &&
                !DateTimeUtil.isValiDateFormat(activityInfo.getDetailStartTime()) &&
                !DateTimeUtil.isValiDateFormat(activityInfo.getDetailEndTime())) {
            return ResponseObject.failStatusMessage("时间格式不正确");
        }
        if (DateTimeUtil.strToDate(activityInfo.getDetailStartTime())
                .compareTo(DateTimeUtil.strToDate(activityInfo.getEndtime())) <= 0) {
            return ResponseObject.failStatusMessage("活动开始时间必须大于报名截止时间");
        }
        if (activityInfo.getStatus() == null) {//不能发布一个 报名开始时间比现在时间提前一个小时的活动，即排除用户今天发布一个昨天开始的活动
            Calendar now = Calendar.getInstance();
            now.add(Calendar.SECOND, -360);
            if (DateTimeUtil.strToDate(activityInfo.getStarttime())
                    .compareTo(now.getTime()) < 0) {
                return ResponseObject.failStatusMessage("输入的时间有误");
            }
        }
        //判断输入的开始时间是否小于结束时间
        if (DateTimeUtil.strToDate(activityInfo.getStarttime())
                .compareTo(DateTimeUtil.strToDate(activityInfo.getEndtime())) >= 0) {
            return ResponseObject.failStatusMessage("报名开始时间不能大于结束时间");
        }
        if (DateTimeUtil.strToDate(activityInfo.getDetailStartTime())
                .compareTo(DateTimeUtil.strToDate(activityInfo.getDetailEndTime())) >= 0) {
            return ResponseObject.failStatusMessage("活动开始时间不能大于结束时间");
        }
        return ResponseObject.successStatus();
    }

    @Override
    public ResponseObject updatePosterUrl(Integer userId, Integer activityId, String posterUrl) {
        if (userId == null || activityId == null || posterUrl == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(), ResponseStatus.ERROR_PARAM.getValue());
        }
        int rowCount = activityDao.updatePosterUrl(userId, activityId, posterUrl);
        if (rowCount > 0) {
            return ResponseObject.successStatus();
        }
        return ResponseObject.failStatusMessage("图片上传失败");
    }

    @Override
    public ResponseObject validateActivityInfo(ActivityInfo activityInfo) {
        //1.验证输入的时间是否正确
        ResponseObject validateTime = validateActivityTime(activityInfo);
        if (!validateTime.isSuccess()) {
            return validateTime;
        }
        //2.验证当前用户的关联事件是否存在时间上的冲突
        ResponseObject validateUserTime = vaildateUserTime(activityInfo, activityInfo.getUserId());
        if (!validateUserTime.isSuccess()) {
            return validateUserTime;
        }
        return ResponseObject.successStatus();
    }

    @Override
    public ResponseObject<ActivityInfo> createChargeActivity(ActivityInfo activityInfo) {
        //3.将数据插入到数据库
        //获取schoolId
        if (StringUtils.isNotBlank(activityInfo.getSchool()) &&
                StringUtils.isNotBlank(activityInfo.getLocation())) {
            Integer schoolId = schoolService.
                    getSchoolIdByProNameSchName(activityInfo.getLocation(), activityInfo.getSchool());
            activityInfo.setSchoolId(schoolId);
        }
        int rowCount = activityDao.createFreeActivity(activityInfo);
        if (rowCount > 0) {
            //创建成功
            //4.修改well_start_activity;
            activityDao.createStartActivity(activityInfo.getId(), activityInfo.getUserId());
            return ResponseObject.successStautsData(activityInfo);
        }
        return ResponseObject.failStatusMessage("创建失败");
    }
}
