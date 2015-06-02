package com.common.manage.view;


import com.common.manage.entity.Role;
import com.common.manage.entity.UserInfo;
import com.common.manage.service.AccountService;
import com.common.manage.service.RoleService;
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
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @classDescription:
 * @author:xiayingjie
 * @createTime:13-10-15 下午3:34
 */
@Controller
@RequestMapping(value = "/admin/user")
public class UserController extends BaseController{
    private String path = "/admin/user";
    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleService roleService;

    //查询
    @RequiresPermissions("admin:user:view")
    @RequestMapping(value = {"", "list"})
    public String list(HttpServletRequest request, HttpServletResponse response, QueryDTO queryDTO) {

        super.list(request,response,queryDTO,accountService,UserInfo.class,path);
        return "/manage/account/adminUserList";
    }

    //去增加
    @RequiresPermissions("admin:user:edit")
    @RequestMapping(value = "toAdd")
    public String toAdd(Model model) {
        return "/manage/account/addUser";
    }

    //增加
    @RequiresPermissions("admin:user:edit")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String toAdd(UserInfo userInfo, HttpServletRequest request) {

        this.accountService.save(userInfo);
        //跳转
        String currentPage = (String) request.getSession().getAttribute("currentPage");
        return "redirect:" + currentPage;
    }

    //去修改
    @RequiresPermissions("admin:user:edit")
    @RequestMapping(value = "toAlter/{id}")
    public String toAlter(Model model, @PathVariable("id") int id) {
        UserInfo userInfo = this.accountService.findById(id);
        model.addAttribute("userInfo", userInfo);
        return "/manage/account/alterUser";
    }

    //修改
    @RequiresPermissions("admin:user:edit")
    @RequestMapping(value = "alter", method = RequestMethod.POST)
    public String alter(HttpServletRequest request, UserInfo userInfo) {

        UserInfo user = this.accountService.findById(userInfo.getId());
        userInfo.setUserName(user.getUserName());
        userInfo.setCreateTime(user.getCreateTime());
        this.accountService.save(userInfo);
        //跳转
        String currentPage = (String) request.getSession().getAttribute("currentPage");
        return "redirect:" + currentPage;
    }

    //删除
    @RequiresPermissions("admin:user:edit")
    @RequestMapping(value = "delete/{id}")
    public String delete(HttpServletRequest request, @PathVariable("id") int id) {
        accountService.deleteById(id);
        //跳转
        String currentPage = (String) request.getSession().getAttribute("currentPage");
        return "redirect:" + currentPage;
    }

    //去设置角色
    @RequiresPermissions("admin:user:edit")
    @RequestMapping(value = "toSetRole/{id}")
    public String toSetRole(HttpServletRequest request, @PathVariable("id") int id) {
        UserInfo user = this.accountService.findById(id);
        if (user != null) {
            request.setAttribute("user", user);
        }
        request.setAttribute("roleList", roleService.findAll());

        return "/manage/account/setRole";
    }

    //设置角色
    @RequiresPermissions("admin:user:edit")
    @RequestMapping(value = "setRole")
    public String setRole(HttpServletRequest request, String[] roles, int id) {
        UserInfo user = this.accountService.findById(id);

        Set roleSet = new HashSet();
        if (user != null) {
            if (roles != null) {
                for (String roleId : roles) {
                    Role role = new Role();
                    role.setId(Integer.parseInt(roleId));
                    roleSet.add(role);
                }
            }
            user.setRoles(roleSet);
            accountService.save(user);
        }

        //跳转
        String currentPage = (String) request.getSession().getAttribute("currentPage");
        return "redirect:" + currentPage;
    }

    //检测用户名
    @RequestMapping(value = "checkName")
    public String checkUserName(HttpServletResponse response, String fieldId, String fieldValue) throws IOException {
        UserInfo userInfo = this.accountService.findByUsername(fieldValue);
        boolean flag = true;
        if (null != userInfo) {
            flag = false;
        }
        String out = "{\"jsonValidateReturn\":[\"" + fieldId + "\"," + flag + "]}";

        response.getWriter().write(out);

        return null;

    }


}
