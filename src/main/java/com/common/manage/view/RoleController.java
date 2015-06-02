package com.common.manage.view;


import com.common.manage.entity.Action;
import com.common.manage.entity.Menu;
import com.common.manage.entity.Role;
import com.common.manage.service.MenuService;
import com.common.manage.service.ModuleService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @classDescription:
 * @author:xiayingjie
 * @createTime:13-10-15 下午3:34
 */
@Controller
@RequestMapping(value = "/admin/role")
public class RoleController extends BaseController{
    private String path="/admin/role";
    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private ModuleService moduleService;



    //查询
    @RequiresPermissions("admin:role:view")
    @RequestMapping(value={"","list"})
    public String list(HttpServletRequest request, HttpServletResponse response,QueryDTO queryDTO) {

        super.list(request,response,queryDTO,roleService,Role.class,path);
        return "/manage/account/adminRoleList";
    }

    //去增加
    @RequiresPermissions("admin:role:edit")
    @RequestMapping(value="toAdd")
    public String toAdd(Model model) {
        return "/manage/account/addRole";
    }
    //增加
    @RequiresPermissions("admin:role:edit")
    @RequestMapping(value="add",method = RequestMethod.POST)
    public String toAdd(Role roleInfo,HttpServletRequest request) {
        this.roleService.save(roleInfo);
        //跳转
        String currentPage= (String) request.getSession().getAttribute("currentPage");
        return "redirect:"+currentPage;
    }
    //去修改
    @RequiresPermissions("admin:role:edit")
    @RequestMapping(value="toAlter/{id}")
    public String toAlter(Model model,@PathVariable("id")int id) {
        Role roleInfo=this.roleService.findById(id);
        model.addAttribute("roleInfo",roleInfo);
        return "/manage/account/alterRole";
    }
    //修改
    @RequiresPermissions("admin:role:edit")
    @RequestMapping(value="alter",method = RequestMethod.POST)
    public String alter(HttpServletRequest request,Role roleInfo ) {
        Role role=this.roleService.findById(roleInfo.getId());
        roleInfo.setCreateTime(role.getCreateTime());
        this.roleService.save(roleInfo);
        //跳转
        String currentPage= (String) request.getSession().getAttribute("currentPage");
        return "redirect:"+currentPage;
    }
    //删除
    @RequiresPermissions("admin:role:edit")
    @RequestMapping(value="delete/{id}")
    public String delete(HttpServletRequest request,@PathVariable("id") int id) {
        roleService.deleteById(id);
        //跳转
        String currentPage= (String) request.getSession().getAttribute("currentPage");
        return "redirect:"+currentPage;
    }

    //去设置菜单
    @RequiresPermissions("admin:role:edit")
    @RequestMapping(value = "toSetMenu/{id}")
    public String toSetMenu(HttpServletRequest request, @PathVariable("id") int id) {
        Role role= this.roleService.findById(id);
        if (role != null) {
            request.setAttribute("role", role);
        }
        List<Menu> menuList = menuService.findAll();

        request.setAttribute("menuList",menuList);

        return "/manage/account/setMenu";
    }

    //设置菜单
    @RequiresPermissions("admin:role:edit")
    @RequestMapping(value = "setMenu")
    public String setMenu(HttpServletRequest request, String[] menus, int id) {
        Role role =roleService.findById(id);

        Set menuSet = new HashSet();
        if (role != null) {
            if (menus != null) {
                for (String menuId : menus) {
                    Menu menu = new Menu();
                    menu.setId(Integer.parseInt(menuId));
                    menuSet.add(menu);
                }
            }

            role.setMenus(menuSet);
            roleService.save(role);
        }

        //跳转
        String currentPage = (String) request.getSession().getAttribute("currentPage");
        return "redirect:" + currentPage;
    }

    //去设置权限
    @RequiresPermissions("admin:role:edit")
    @RequestMapping(value = "toSetAction/{id}")
    public String toSetAction(HttpServletRequest request, @PathVariable("id") int id) {
        Role role= this.roleService.findById(id);
        if (role != null) {
            request.setAttribute("role", role);
        }
        request.setAttribute("moduleList",moduleService.findAll());

        return "/manage/account/setAction";
    }

    //设置权限
    @RequiresPermissions("admin:role:edit")
    @RequestMapping(value = "setAction")
    public String setAction(HttpServletRequest request, String[] actions, int id) {
        Role role =roleService.findById(id);

        Set actionSet = new HashSet();
        if (role != null) {
            if (actions != null) {
                for (String actionId : actions) {
                    Action action = new Action();
                    action.setId(Integer.parseInt(actionId));
                    actionSet.add(action);
                }
            }

            role.setActions(actionSet);
            roleService.save(role);
        }

        //跳转
        String currentPage = (String) request.getSession().getAttribute("currentPage");
        return "redirect:" + currentPage;
    }


}
