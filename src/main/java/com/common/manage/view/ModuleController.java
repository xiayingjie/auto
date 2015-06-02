package com.common.manage.view;


import com.common.manage.entity.Module;
import com.common.manage.service.ModuleService;
import com.common.manage.tool.query.QueryDTO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @classDescription:
 * @author:xiayingjie
 * @createTime:13-10-15 下午3:34
 */
@Controller
@RequestMapping(value = "/admin/module")
public class ModuleController extends BaseController{
    private String path="/admin/module";
    @Autowired
    private ModuleService moduleService;
    //查询
    @RequiresPermissions("admin:module:view")
    @RequestMapping(value={"","list"})
    public String list(HttpServletRequest request, HttpServletResponse response,QueryDTO queryDTO) {

        super.list(request,response,queryDTO,moduleService, Module.class,path);
        return "/manage/account/adminModuleList";
    }

    //去增加
    @RequiresPermissions("admin:module:edit")
    @RequestMapping(value="toAdd")
    public String toAdd(Model model) {
        return "/manage/account/addModule";
    }
    //增加
    @RequiresPermissions("admin:module:edit")
    @RequestMapping(value="add",method = RequestMethod.POST)
    public String toAdd(Module moduleInfo,HttpServletRequest request) {
        this.moduleService.save(moduleInfo);
        //跳转
        String currentPage= (String) request.getSession().getAttribute("currentPage");
        return "redirect:"+currentPage;
    }
    //去修改
    @RequiresPermissions("admin:module:edit")
    @RequestMapping(value="toAlter/{id}")
    public String toAlter(Model model,@PathVariable("id")int id) {
        Module moduleInfo=this.moduleService.findById(id);
        model.addAttribute("moduleInfo",moduleInfo);
        return "/manage/account/alterModule";
    }
    //修改
    @RequiresPermissions("admin:module:edit")
    @RequestMapping(value="alter")
    public String alter(HttpServletRequest request,Module moduleInfo) {
        Module module=this.moduleService.findById(moduleInfo.getId());
        moduleInfo.setCreateTime(module.getCreateTime());
        this.moduleService.save(moduleInfo);
        //跳转
        String currentPage= (String) request.getSession().getAttribute("currentPage");
        return "redirect:"+currentPage;
    }
    //删除
    @RequiresPermissions("admin:module:edit")
    @RequestMapping(value="delete/{id}")
    public String delete(HttpServletRequest request,@PathVariable("id") int id) {
        moduleService.deleteById(id);
        //跳转
        String currentPage= (String) request.getSession().getAttribute("currentPage");
        return "redirect:"+currentPage;
    }




}
