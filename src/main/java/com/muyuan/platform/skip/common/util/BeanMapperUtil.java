package com.muyuan.platform.skip.common.util;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.MappingException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fanwenwu
 */
public class BeanMapperUtil {
    /**
     * 对象复制
     */
    private static DozerBeanMapper mapper;

    private BeanMapperUtil(){}
    
    /**
     * @return
     */
    private synchronized static Mapper getMapper() {
        if (mapper == null) {
            mapper = new DozerBeanMapper();
        }

        return mapper;
    }

    /**
     * 对象值复制..
     * 
     * @param source
     * @param destinationClass
     * @return 复制后的对象.
     */
    public static <T> T map(Object source, Class<T> destinationClass) throws MappingException {
        if (source == null) {
            return null;
        }
        
        return getMapper().map(source, destinationClass);
    }

    /**
     * 复制一个对象列表...
     * 
     * @param sources
     * @param destinationClass
     * @return
     * @throws MappingException
     */
    public static <T> List<T> map(List<? extends Object> sources, Class<T> destinationClass) throws MappingException {
        List<T> rtn = new ArrayList<T>();
        for (Object s : sources) {
            T temp = getMapper().map(s, destinationClass);
            rtn.add(temp);
        }
        return rtn;
    }

    /**
     * 对象值复制.
     * 
     * @param source
     *            复制源对象.
     * @param destination
     *            复制输出对象.
     */
    public static void map(Object source, Object destination) throws MappingException {
        if (source != null) {
            getMapper().map(source, destination);
        }
    }
}