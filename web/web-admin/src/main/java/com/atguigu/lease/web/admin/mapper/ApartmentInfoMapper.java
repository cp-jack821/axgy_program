package com.atguigu.lease.web.admin.mapper;

import com.atguigu.lease.model.entity.ApartmentInfo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
* @author chenp
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Mapper
* @createDate 2023-08-02 15:48:00
* @Entity com.atguigu.lease.model.ApartmentInfo
*/
public interface ApartmentInfoMapper extends BaseMapper<ApartmentInfo> {

    // 对于Mapper接口中的自定义需要分页的方法，语法上是这么要求
    // 第一个参数是传入IPage，这个参数只需要包含两个信息：size，current，其中的list(getRecords())实际为空
    // 当我们执行一个SQL查询，不需要考虑分页，然后拦截器会工作，会动态的拼凑SQL语句，拼接limit自居，查询结果回调用setRecords()
    IPage<ApartmentItemVo> pageApartmentItemByQuery(IPage<ApartmentItemVo> page, ApartmentQueryVo queryVo);
}




