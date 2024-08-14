package com.atguigu.lease.web.admin.custom.converter;

import com.atguigu.lease.model.enums.ItemType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
public class StringToItemTypeConverter implements Converter<String,ItemType> {
    public ItemType convert(String source) {
        //ItemType.values() 获取ItemType这个枚举类型中的所有选项值
        return Arrays.stream(ItemType.values())
                .filter(itemType -> source.equals(""+itemType.getCode()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new) ;
    }
}
