package com.cj.core.util;


import java.util.Map;

public class ObjectUtil {

    /**
     * 使用org.apache.commons.beanutils进行转换
     */


    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        org.apache.commons.beanutils.BeanUtils.populate(obj, map);

        return obj;
    }


}
