package com.common.manage.view;




import com.common.manage.entity.UserInfo;
import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.Reflection;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.GsonBuilderUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import com.common.manage.entity.UserInfo;

/**
 * @classDescription:
 * @author:xiayingjie
 * @createTime:15/5/25
 */

public class Test {
    public static void main(String[] args) throws JSONException, IllegalAccessException {
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
        HashMap mm;

        ImmutableMap m = ImmutableMap
                .copyOf(mm=new HashMap() {
                    {
                        put("First", "1");
                        put("Second", "2");
                        put("Third", "3");
                        put("First1", "11");
                        put("Second1", "22");
                        put("Third2", "31");
                        put("Third22", "232");
                    }
                });

  //      System.out.println(gson.toJson(mm));

  //      filter(UserInfo.class, null);
        UserInfo u=new UserInfo();
        u.setUserName("sssss");
        System.out.printf("---");
        filter(u,null);



    }

    public static Map filter(Object bean,String filter) throws IllegalAccessException {
        try {
            BeanInfo b=Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors =  b.getPropertyDescriptors();
            for (PropertyDescriptor p:propertyDescriptors) {
                System.out.println(p.getName() + ":" + p.getValue(p.getName()));
                if (!p.getName().equals("class")) {
                    Method readMethod = p.getReadMethod();
                    Object obj= null;
                    try {
                        obj = readMethod.invoke(bean, new Object[0]);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    System.out.println(String.valueOf(obj));

                }

            }
            } catch (IntrospectionException e) {
            e.printStackTrace();
        }

//        for(Field f:field){
//            f.setAccessible(true); // 设置属性可以访问
//            System.out.println(f.getName());
//        }
        return null;
    }

//    public static Map merge(){
//
//    }
}
