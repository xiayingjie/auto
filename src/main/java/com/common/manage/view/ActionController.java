package com.common.manage.view;


import com.common.manage.entity.Action;
import com.common.manage.service.ActionService;
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
@RequestMapping(value = "/admin/action")
public class ActionController extends BaseController{
    private String path="/admin/action";
    @Autowired
    private ActionService actionService;

    @Autowired
    private ModuleService moduleService;
    //查询
    @RequiresPermissions("admin:action:view")
    @RequestMapping(value={"","list"})
    public String list(HttpServletRequest request, HttpServletResponse response,QueryDTO queryDTO) {

        super.list(request,response,queryDTO,actionService,Action.class,path);
        return "/manage/account/adminActionList";
    }

    //去增加
    @RequiresPermissions("admin:action:edit")
    @RequestMapping(value="toAdd")
    public String toAdd(Model model) {
        model.addAttribute("moduleList",this.moduleService.findAll());
        return "/manage/account/addAction";
    }
    //增加
    @RequiresPermissions("admin:action:edit")
    @RequestMapping(value="add",method = RequestMethod.POST)
    public String toAdd(Action actionInfo,HttpServletRequest request,int moduleId) {
        actionInfo.setModule(this.moduleService.findById(moduleId));
        this.actionService.save(actionInfo);

        //跳转
        String currentPage= (String) request.getSession().getAttribute("currentPage");
        return "redirect:"+currentPage;
    }
    //去修改
    @RequiresPermissions("admin:action:edit")
    @RequestMapping(value="toAlter/{id}")
    public String toAlter(Model model,@PathVariable("id")int id) {
        Action actionInfo=this.actionService.findById(id);
        model.addAttribute("moduleList",this.moduleService.findAll());
        model.addAttribute("actionInfo",actionInfo);
        return "/manage/account/alterAction";
    }
    //修改
    @RequiresPermissions("admin:action:edit")
    @RequestMapping(value="alter")
    public String alter(HttpServletRequest request,Action actionInfo,int moduleId) {
        Action action=this.actionService.findById(actionInfo.getId());
        actionInfo.setModule(this.moduleService.findById(moduleId));
        actionInfo.setCreateTime(action.getCreateTime());
        this.actionService.save(actionInfo);
        //跳转
        String currentPage= (String) request.getSession().getAttribute("currentPage");
        return "redirect:"+currentPage;
    }
    //删除
    @RequiresPermissions("admin:action:edit")
    @RequestMapping(value="delete/{id}")
    public String delete(HttpServletRequest request,@PathVariable("id") int id) {
        actionService.deleteById(id);
        //跳转
        String currentPage= (String) request.getSession().getAttribute("currentPage");
        return "redirect:"+currentPage;
    }




}
