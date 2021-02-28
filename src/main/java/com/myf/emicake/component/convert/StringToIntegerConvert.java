package com.myf.emicake.component.convert;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @ClassName com.myf.emicake.component StringToIntegerConvert
 * @Description String转Integer
 * @Author Afengis
 * @Date 2021/2/14 15:56
 * @Version V1.0
 **/
@Component
public class StringToIntegerConvert implements Converter<String,Integer> {

    @Override
    public Integer convert(String source) {
        if (StringUtils.isNumeric(source)){
            return Integer.valueOf(source);
        }

        return -1;
    }
}
