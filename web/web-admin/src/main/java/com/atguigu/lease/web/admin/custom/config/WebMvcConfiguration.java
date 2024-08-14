package com.atguigu.lease.web.admin.custom.config;

import com.atguigu.lease.web.admin.custom.converter.StringToAppointmentStatusConverter;
import com.atguigu.lease.web.admin.custom.converter.StringToBaseEnumConverterFactory;
import com.atguigu.lease.web.admin.custom.converter.StringToItemTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfiguration implements WebMvcConfigurer {

    //@Autowired
    //private StringToItemTypeConverter stringToItemTypeConverter ;

    //@Autowired
    //private StringToAppointmentStatusConverter stringToAppointmentStatusConverter;

    @Autowired
    private StringToBaseEnumConverterFactory stringToBaseEnumConverterFactory ;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        //registry.addConverter(stringToItemTypeConverter);
        //registry.addConverter(stringToAppointmentStatusConverter);
        registry.addConverterFactory(stringToBaseEnumConverterFactory);
    }
}
