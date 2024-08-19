package com.atguigu.lease.web.admin.mapper;

import com.atguigu.lease.model.entity.AttrKey;
import com.atguigu.lease.web.admin.vo.attr.AttrKeyVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author chenp
* @description 针对表【attr_key(房间基本属性表)】的数据库操作Mapper
* @createDate 2024-08-19 13:29:00
* @Entity com.atguigu.lease.model.AttrKey
*/
public interface AttrKeyMapper extends BaseMapper<AttrKey> {

    List<AttrKeyVo> getAttrKeyVoList();
}




