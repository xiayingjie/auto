package com.common.manage.view;

import com.common.manage.entity.Menu;
import com.common.manage.entity.UserInfo;
import com.common.manage.service.AccountService;
import com.common.manage.service.MenuService;
import com.common.manage.tool.BeanInfoUtil;
import com.common.manage.tool.query.PropertyFilter;
import com.common.manage.tool.query.QueryDTO;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @classDescription: test
 * @author:xiayingjie
 * @createTime:15/5/26
 */
@Controller
@RequestMapping(value = "/head")
public class HeadJsonController extends BaseController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private MenuService    menuService;

    //测试
    @RequestMapping(value = "list/{page}/{size}")
    public void json(HttpServletRequest request,HttpServletResponse response, @PathVariable("page") int page,@PathVariable("size") int size) {

        Page p=super.headList(accountService, UserInfo.class);
        List list=  BeanInfoUtil.list2map(p.getContent(),"roles");
        Map map=getSuccess();
        map.put("data", list);
        super.out(response, map);
    }



}