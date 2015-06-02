package com.common.manage.view;


import com.common.manage.entity.Menu;
import com.common.manage.service.MenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @classDescription:
 * @author:xiayingjie
 * @createTime:13-10-15 下午3:34
 */
@Controller
@RequestMapping(value = "/admin/menu")
public class MenuController extends BaseController{
    private String path="/admin/menu";
    @Autowired
    private MenuService menuService;

    //查询所有的结果
    @RequiresPermissions("admin:menu:view")
    @RequestMapping(value={"","list"})
    public String list(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("menuList",this.menuService.findAll());
        request.getSession().setAttribute("currentPage",path);
        return "/manage/account/adminMenuList";
    }

    //去增加
    @RequiresPermissions("admin:menu:edit")
    @RequestMapping(value="toAdd/{id}")
    public String toAdd(Model model,@PathVariable("id")int id,String orders) {
        model.addAttribute("menuId",id);
        model.addAttribute("orders",orders);
        return "/manage/account/addMenu";
    }
    //增加
    @RequiresPermissions("admin:menu:edit")
    @RequestMapping(value="add",method = RequestMethod.POST)
    public String toAdd(Menu menuInfo,HttpServletRequest request) {
        this.menuService.save(menuInfo);
        //跳转
        String currentPage= (String) request.getSession().getAttribute("currentPage");
        return "redirect:"+currentPage;
    }
    //去修改
    @RequiresPermissions("admin:menu:edit")
    @RequestMapping(value="toAlter/{id}")
    public String toAlter(Model model,@PathVariable("id")int id) {
        Menu menuInfo=this.menuService.findById(id);
        model.addAttribute("menuInfo",menuInfo);
        return "/manage/account/alterMenu";
    }
    //修改
    @RequestMapping(value="alter",method = RequestMethod.POST)
    public String alter(HttpServletRequest request,Menu menuInfo) {
        Menu menu=this.menuService.findById(menuInfo.getId());
        menuInfo.setCreateTime(menu.getCreateTime());
        this.menuService.save(menuInfo);
        //跳转
        String currentPage= (String) request.getSession().getAttribute("currentPage");
        return "redirect:"+currentPage;
    }
    //删除
    @RequiresPermissions("admin:menu:edit")
    @RequestMapping(value="delete/{id}")
    public String delete(HttpServletRequest request,@PathVariable("id") int id) {

        menuService.deleteById(id);
        //跳转
        String currentPage= (String) request.getSession().getAttribute("currentPage");
        return "redirect:"+currentPage;
    }




}
