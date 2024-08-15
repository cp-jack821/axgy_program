package com.atguigu.lease.web.admin.custom.converter;

import com.atguigu.lease.model.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Arrays;

public class StringToBaseEnumConverterFactory implements ConverterFactory<String, BaseEnum> {
    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return new Converter<String, T>() {

            //获取枚举中的所有可选值有两种情况：
            //① 具体的枚举类型： ItemType -> ItemType.values()
            //② 已知Class： Class中提供了一个方法：getEnumConstants

            @Override
            public T convert(String source) {
                Arrays.stream(targetType.getEnumConstants()).filter(t -> source.equals(t.getCode() + ""))
                        .findFirst().orElseThrow(RuntimeException::new);
                return null;
            }
        };
    }
}
