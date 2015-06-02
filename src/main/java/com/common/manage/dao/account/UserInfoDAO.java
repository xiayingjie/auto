package com.common.manage.dao.account;


import com.common.manage.dao.BaseDAO;
import com.common.manage.entity.UserInfo;


/**
 * @classDescription:用户操作DAO
 * @author:xiayingjieø
 * @createTime:2013-9-13
 */

public interface UserInfoDAO extends BaseDAO<UserInfo,Integer> {

    /**
     * 根据用户名查询用户
     * @param userName
     * @return
     */
    public UserInfo findByUserName(String userName);


}
