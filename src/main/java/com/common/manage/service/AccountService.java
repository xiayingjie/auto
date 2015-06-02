package com.common.manage.service;


import com.common.manage.dao.BaseDAO;
import com.common.manage.dao.account.UserInfoDAO;
import com.common.manage.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @classDescription:账号管理service
 * @author:xiayingjie
 * @createTime:13-10-15 下午3:27
 */
@Service
@Transactional
public class AccountService extends BaseService<UserInfo>{


    @Autowired
    private UserInfoDAO userInfoDAO;

    /**
     * 根据用户名密码进行登陆
     *
     * @param userName
     * @param password
     * @return
     */
    public UserInfo login(String userName, String password) {
        UserInfo u = this.userInfoDAO.findByUserName(userName);
        if (null != u) {
            if (u.getUserPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    /**
     * 根据用户名查找到相关的用户
     *
     * @param userName
     * @return
     */
    public UserInfo findByUsername(String userName) {
        return this.userInfoDAO.findByUserName(userName);
    }



    @Override
    protected BaseDAO getDAO() {
        return userInfoDAO;
    }
}
