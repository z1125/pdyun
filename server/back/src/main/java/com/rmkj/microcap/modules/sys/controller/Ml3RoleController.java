package com.rmkj.microcap.modules.sys.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.modules.sys.bean.Ml3RoleBean;
import com.rmkj.microcap.modules.sys.bean.Ml3RolePermissionBean;
import com.rmkj.microcap.modules.sys.service.Ml3PermissionService;
import com.rmkj.microcap.modules.sys.service.Ml3RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by Administrator on 2016-11-15.
*/
@Controller
@RequestMapping("/M3Role")
public class Ml3RoleController extends BaseController {
    @Autowired
    private Ml3RoleService ml3RoleService;
    @Autowired
    private Ml3PermissionService permissionService;
    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("M3Role")
    public String listPage() {
        return "/Ml3Role/M3Role_list";
    }
    /**
    * 新增页面
    * @return
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("M3Role:add")
    public String addPage() {
         return "/Ml3Role/Ml3Role_add";
    }
    /**
    * 编辑页面
    * @param id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresPermissions("M3Role:edit")
    public String editPage(String id,Map<String,Object> model) {
        model.put("model", ml3RoleService.get(id));
        return "/Ml3Role/Ml3Role_edit";
    }
    /**
    * 列表数据
    * @param entity
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("M3Role")
    public GridPager listData(Ml3RoleBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<Ml3RoleBean> entityList = ml3RoleService.queryList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }
    /**
    * 保存
    * @param entity
    * @param errors
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @RequiresPermissions("M3Role:add")
    public ExecuteResult save(@Valid Ml3RoleBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        ml3RoleService.insert(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 更新
    * @param entity
    * @param errors
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @RequiresPermissions("M3Role:edit")
    public ExecuteResult update(@Valid Ml3RoleBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        ml3RoleService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("M3Role:delete")
    public ExecuteResult delete(String id) {
        ml3RoleService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }

    @RequestMapping(value = "/setMenu", method = RequestMethod.GET)
    public String roleMenuPage(String id, Map<String, Object> model) {
        model.put("roleId", id);
        return "/Ml3Role/Ml3role_menu";
    }
    @ResponseBody
    @RequestMapping(value = "/menu_trees", method = RequestMethod.POST)
    public Map<String, Object> menuTreeData(String roleId) {
        Map<String, Object> map = new HashMap<>();
        map.put("menuTrees", permissionService.findMenuTree());
        map.put("selectMenuIds", ml3RoleService.findSelectMenuByRoleId(roleId));
       return map;
    }

    @ResponseBody
    @RequestMapping(value = "/saveRoleMenu", method = RequestMethod.POST)
    public ExecuteResult saveRoleMenu(String roleId, @RequestBody List<Ml3RolePermissionBean> sysRoleMenuBeans) {
        ml3RoleService.insertRoleMenu(roleId, sysRoleMenuBeans);
        return new ExecuteResult(StatusCode.OK);
    }
}
