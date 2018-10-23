package org.sc.service.ps.service;

import java.util.ArrayList;
import java.util.List;

import org.sc.common.dao.service.BaseLogicServiceImpl;
import org.sc.common.enmus.DelEnum;
import org.sc.common.exception.ServiceRunTimeException;
import org.sc.common.utils.ShaUtils;
import org.sc.common.utils.StringUtils;
import org.sc.service.ps.dao.UserDao;
import org.sc.service.ps.enums.ServiceErrorCode;
import org.sc.facade.ps.constant.UserStatus;
import org.sc.facade.ps.constant.UserType;
import org.sc.facade.ps.model.attribute.Wallet;
import org.sc.facade.ps.model.table.User;
import org.sc.facade.ps.model.vo.IdNameVo;
import org.sc.facade.ps.service.RedisService;
import org.sc.facade.ps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends BaseLogicServiceImpl<User> implements UserService{
	public UserServiceImpl(@Autowired UserDao userDao) {
		super(userDao);
		this.userDao = userDao;
	}
	@Autowired
	private UserDao userDao;
	
    @Autowired
    private RedisService redisService;
		
	@Override
	public List<User> getUsers() {
		List<User> Users = userDao.findAll();
		return Users;
	}

	@Override
	public User findByUserName(String userName) {
		return userDao.findByUserName(userName);
	}

    @Override
    public User login(User user) {
        List<User> users = new ArrayList<>();
        users = userDao.loginByUserName(user.getUserName(), ShaUtils.sha512(user.getPassword()));
        if (users.size() == 0) {//手机号和密码登录
            User queryParam = new User();
            queryParam.setDel(DelEnum.VALID.getType());
            queryParam.setPassword(ShaUtils.sha512(user.getPassword()));
            queryParam.setPhone(user.getUserName());
            users = userDao.findAll(Example.of(queryParam));
        }
       
        if (users.size() == 0) {
            throw new ServiceRunTimeException(ServiceErrorCode.WRONG_LOGIN);
        } else if (users.size() > 1) {
            throw new ServiceRunTimeException(ServiceErrorCode.WRONG_ACCOUNT);
        }

        User returnUser = users.get(0);
        if (UserStatus.FROZEN == returnUser.getStatus()) {
            throw new ServiceRunTimeException(ServiceErrorCode.FROZEN_ACCOUNT);
        }
        if (UserStatus.NO_MAIL == returnUser.getStatus()) {
            throw new ServiceRunTimeException(ServiceErrorCode.NEED_CHECK_EMAIL);
        }
        return returnUser;
    }
    
    @Override
    public String addUser(User user) {
        checkUserAttribute(user);
        user.setPassword(ShaUtils.sha512(user.getPassword()));
        user.setPayPassword(user.getPassword());

        if (user.getUserType() == null) {
            user.setUserType(UserType.PERSONAL.getType());
        }

        Wallet wallet = initUserWallet();
        user.setWallet(wallet);

        User returnUser = userDao.save(user);
        if (returnUser == null) {
            throw new ServiceRunTimeException(ServiceErrorCode.WRONG_ADD);
        }

        reloadRedisUserIdNames();

        return returnUser.getId();
    }
    
    private void reloadRedisUserIdNames() {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    User queryParam = new User();
                    queryParam.setDel(DelEnum.VALID.getType());
                    List<User> users = getListByEntity(queryParam);

                    List<IdNameVo> idNameVos = new ArrayList<>();
                    users.forEach(user -> idNameVos.add(new IdNameVo(user.getId(), user.getUserName())));
                    redisService.setList("UserIdNames", idNameVos);
                }
            }).start();
        } catch (Exception e) {
            this.logger.error("更新redis里的用户id,userName的线程启动失败", e);
        }
    }

	/**
     * 初始化用户钱包
     *
     * @return
     */
    private Wallet initUserWallet() {
        Wallet wallet = new Wallet();
        wallet.setMoney(0L);
        wallet.setPoint(0L);
        wallet.setDeposit(0L);
        wallet.setCreditMoney(0L);
        return wallet;
    }
    
    /**
     * 检测用户的用户名和（手机号或邮箱）
     *
     * @param user
     */
    @Override
    public Boolean checkUserAttribute(User user) {
        if (StringUtils.isNotBlank(user.getUserName())) {
            if (userDao.checkUserName(user.getUserName()) > 0) {
                throw new ServiceRunTimeException(ServiceErrorCode.REPEAT_USERNAME);
            }
        }
        if (StringUtils.isNotBlank(user.getPhone())) {
            User checkParam = new User();
            checkParam.setDel(DelEnum.VALID.getType());
            checkParam.setPhone(user.getPhone());
            if (userDao.exists(Example.of(checkParam))) {
                throw new ServiceRunTimeException(ServiceErrorCode.REPEAT_PHONE);
            }
        }
        if (StringUtils.isNotBlank(user.getEmail())) {
            User checkParam = new User();
            checkParam.setDel(DelEnum.VALID.getType());
            checkParam.setEmail(user.getEmail());
            if (userDao.exists(Example.of(checkParam))) {
                throw new ServiceRunTimeException(ServiceErrorCode.REPEAT_EMAIL);
            }
        }

        return true;
    }
    
    @Override
    public List<User> getListByEntity(User user) {
        return userDao.findAll(Example.of(user), new Sort(Sort.Direction.DESC, "updateTime"));
    }
}
