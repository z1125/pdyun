package com.rmkj.microcap.modules.parameterset.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.modules.parameterset.entity.ParameterSetBean;
import com.rmkj.microcap.modules.parameterset.service.ParameterSetService;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
* Created by Administrator on 2016-12-16.
*/
@Controller
@RequestMapping("/parameterset")
public class ParameterSetController extends BaseController {
    @Autowired
    private ParameterSetService parameterSetService;

    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("parameterset")
    public String listPage() {
        return "/parameterset/parameterset_list";
    }
    /**
    * 新增页面
    * @return
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("parameterset:add")
    public String addPage() {
         return "/parameterset/parameterset_add";
    }
    /**
    * 编辑页面
    * @param id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresPermissions("parameterset:edit")
    public String editPage(String id,Map<String,Object> model) {
        model.put("model", parameterSetService.get(id));
        return "/parameterset/parameterset_edit";
    }

    @RequestMapping(value = "/platMoneyDetail", method = RequestMethod.GET)
    public String getPlatMoneyPage(Map<String,Object> model) {
        return "/parameterset/plat_money_list";
    }

//    @ResponseBody
//    @RequestMapping(value = "/platMoneyDetailList", method = RequestMethod.POST)
//    public GridPager platMoneyDetailList(ParameterSetBean entity) {
//        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
//        List<ParameterSetBean> entityList = parameterSetService.queryList(entity);
//        GridPager g = new GridPager();
//        g.setTotal(MybatisPagerInterceptor.getTotal());
//        g.setRows(entityList);
//        return g;
//    }


    /**
    * 列表数据
    * @param entity
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("parameterset")
    public GridPager listData(ParameterSetBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<ParameterSetBean> entityList = parameterSetService.queryList(entity);
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
//    @ResponseBody
//    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    @RequiresPermissions("parameterset:add")
//    public ExecuteResult save(@Valid ParameterSetBean entity, Errors errors) {
//        if (errors.hasErrors()) {
//            return parseErrors(errors);
//        }
//        parameterSetService.insert(entity);
//        return new ExecuteResult(StatusCode.OK);
//    }
    /**
    * 更新
    * @param entity
    * @param errors
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @RequiresPermissions("parameterset:edit")
    public ExecuteResult update(@Valid ParameterSetBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        parameterSetService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("parameterset:delete")
    public ExecuteResult delete(String id) {
        parameterSetService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }
}
