package com.common.manage.view;

import com.common.manage.service.BaseService;
import com.common.manage.tool.ServletUtil;
import com.common.manage.tool.query.PropertyFilter;
import com.common.manage.tool.query.QueryDTO;
import com.common.manage.tool.query.SpecificationUtil;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @classDescription:
 * @author:xiayingjie
 * @createTime:15/5/21
 */

public abstract class BaseController {

    Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation() //不导出实体中没有用@Expose注解的属性
            .enableComplexMapKeySerialization() //支持Map的key为复杂对象的形式
            .serializeNulls()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")//时间转化为特定格式
//             .setDateFormat(DateFormat.LONG)
                    // .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)//会把字段首字母大写,注:对于实体上使用了@SerializedName注解的不会生效.
            .setPrettyPrinting() //对json结果格式化.
            .setVersion(1.0)    //有的字段不是一开始就有的,会随着版本的升级添加进来,那么在进行序列化和返序列化的时候就会根据版本号来选择是否要序列化.
                    //@Since(版本号)能完美地实现这个功能.还的字段可能,随着版本的升级而删除,那么
                    //@Until(版本号)也能实现这个功能,GsonBuilder.setVersion(double)方法需要调用.
            .create();

    /**
     * 分页整合
     * @param request
     * @param response
     * @param queryDTO
     * @param service
     * @param clz
     * @param path
     */
    public void list(HttpServletRequest request, HttpServletResponse response,QueryDTO queryDTO,BaseService service,Class clz,String path) {

        Map<String,String> searchMap= ServletUtil.getParametersStartingWith(request, "search");

        //查询结果
        Specification specification= SpecificationUtil.bySearchFilter(searchMap, clz);
        PageRequest pageRequest=SpecificationUtil.getPageRequest(queryDTO);
        Page page = service.findAll(specification,pageRequest);
        //获取查询条件
        Map conditionMap= PropertyFilter.parseCondition(searchMap, queryDTO, path);

        PropertyFilter.setAttribute(request,searchMap);
        request.setAttribute("conditionMap",conditionMap);
        request.setAttribute("pageInfo", page);
        request.getSession().setAttribute("currentPage",path+"?"+PropertyFilter.parseUrl(searchMap,queryDTO));

    }
    /**
     * 分页查询
     * @param queryDTO
     * @param service
     * @param clz
     */
    public Page headList(PropertyFilter pf,QueryDTO queryDTO,BaseService service,Class clz) {
        //查询结果
        Specification specification= SpecificationUtil.bySearchFilter(pf.getFilterList(), clz);
        PageRequest pageRequest=SpecificationUtil.getPageRequest(queryDTO);
        Page page = service.findAll(specification, pageRequest);
        return page;

    }
    /**
     * 分页查询
     * @param queryDTO
     * @param service
     * @param clz
     */
    public Page headList(QueryDTO queryDTO,BaseService service,Class clz) {
        return headList(new PropertyFilter(),queryDTO,service,clz);

    }
    /**
     * 分页查询
     * @param service
     * @param clz
     */
    public Page headList(BaseService service,Class clz) {
        return headList(new PropertyFilter(),new QueryDTO(),service,clz);

    }


    /**
     * 获取成功map
     * @return
     */
    public Map<String,Object> getSuccess() {
       return getCode(0,"ok");

    }

    /**
     * 获取失败的map
     * @param code
     * @return
     */
    public Map<String,Object>getFaild(int code){
        return getCode(0,"error");
    }

    /**
     * 自定义map
     * @param code
     * @param msg
     * @return
     */
    public Map<String,Object>getCode(int code,String msg) {
        Map map=Maps.newHashMap();
        map.put("code",code);
        map.put("msg",msg);
        return map;
    }

    //输出
    protected void out(HttpServletResponse response, Map map) {
        try {
            response.setHeader("Content-Type", "application/json");

            response.getWriter().write(gson.toJson(map));

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

}
