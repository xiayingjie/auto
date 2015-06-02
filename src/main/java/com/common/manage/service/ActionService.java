package com.common.manage.service;



import com.common.manage.dao.BaseDAO;
import com.common.manage.dao.account.ActionDAO;
import com.common.manage.entity.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @classDescription:权限管理Service
 * @author:xiayingjie
 * @createTime:15-5-21 17:25
 */
@Service
@Transactional
public class ActionService extends BaseService<Action> {
    @Autowired
    private ActionDAO actionDAO;


    @Override
    protected BaseDAO getDAO() {
        return actionDAO;
    }
}
