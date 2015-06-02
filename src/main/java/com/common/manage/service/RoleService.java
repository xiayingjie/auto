package com.common.manage.service;


import com.common.manage.dao.BaseDAO;
import com.common.manage.dao.account.RoleDAO;
import com.common.manage.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @classDescription:角色管理Service
 * @author:xiayingjie
 * @createTime:15-5-21 17:25
 */
@Service
@Transactional
public class RoleService extends BaseService<Role> {
    @Autowired
    private RoleDAO roleDAO;


    @Override
    protected BaseDAO getDAO() {
        return roleDAO;
    }
}
