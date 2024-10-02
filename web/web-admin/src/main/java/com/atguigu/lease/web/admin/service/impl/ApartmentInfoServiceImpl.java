package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.*;
import com.atguigu.lease.model.enums.ItemType;
import com.atguigu.lease.web.admin.mapper.ApartmentInfoMapper;
import com.atguigu.lease.web.admin.service.*;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenp
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2024-08-20 16:46:00
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {

    @Autowired
    private ApartmentFacilityService apartmentFacilityService;

    @Autowired
    private ApartmentFeeValueService apartmentFeeValueService;

    @Autowired
    private ApartmentLabelService apartmentLabelService;

    @Autowired
    private GraphInfoService graphInfoService;

    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;

    @Override
    public void saveOrUpdateApartment(ApartmentSubmitVo apartmentSubmitVo) {

        Long apartmentId0 = apartmentSubmitVo.getId();
        boolean updateFlag = apartmentId0 != null && apartmentId0 != 0;

        // 1.保存或更新ApartmentInfo自身的信息（insert或update apartmentInfo表）
        super.saveOrUpdate(apartmentSubmitVo);

        //2.如果是更新，则需要先删除掉之前的关系信息
        if (updateFlag){
            //2-1 删除和配套之间的关系
            /*
            apartmentSubmitVo.getFacilityInfoIds().forEach(facilityInfoId -> {

                apartmentFacilityService.remove(new LambdaQueryWrapper<ApartmentFacility>()
                        .eq(ApartmentFacility::getId, facilityInfoId)
                        .eq(ApartmentFacility::getApartmentId,apartmentId));
            });
             */
            apartmentFacilityService.remove(new LambdaQueryWrapper<ApartmentFacility>().eq(ApartmentFacility::getApartmentId,apartmentId0));
            //2-2 删除和标签之间的关系
            apartmentLabelService.remove(new LambdaQueryWrapper<ApartmentLabel>().eq(ApartmentLabel::getApartmentId,apartmentId0));
            //2-3 删除和杂费之间的关系
            apartmentFeeValueService.remove(new LambdaQueryWrapper<ApartmentFeeValue>().eq(ApartmentFeeValue::getApartmentId,apartmentId0));
            //2-4 删除之前的图片
            graphInfoService.remove(new LambdaQueryWrapper<GraphInfo>().eq(GraphInfo::getId,apartmentId0).eq(GraphInfo::getItemType,ItemType.APARTMENT));
        }

        //3.保存公寓的关系信息（和配套的关系、和杂费的关系、和标签的关系、和图片的关系）
        //3-1 保存和配套的关系信息

        //apartmentSubmitVo.getFacilityInfoIds() 得到的是一个List<Long>集合，这个集合保存的是facilityId
        //.stream() 变成流，要进行流式计算
        //.map() 将stream中的每一个元素做一个动作，做完动作之后返回的一个一个的对象，形成一个map流
        //forEach中是方法引用，实力对象名称：实例方法名称
        //使用方法引用的语法前提是：forEach这个方法的输入参数正好是 方法引用的那个方法的输入参数

        Long apartmentId = apartmentSubmitVo.getId();
        apartmentSubmitVo.getFacilityInfoIds().stream().map(facilityId -> new ApartmentFacility(apartmentId,facilityId))
                .forEach(apartmentFacilityService::saveOrUpdate);

        //3-2 保存和杂费的关系信息
        apartmentSubmitVo.getFeeValueIds().stream().map(feeValueId -> new ApartmentFeeValue(apartmentId,feeValueId))
                .forEach(apartmentFeeValueService::saveOrUpdate);

        //3-3 保存和标签的关系
        apartmentSubmitVo.getFeeValueIds().stream().map(labelId -> ApartmentLabel.builder().apartmentId(apartmentId).labelId(labelId).build())
                .forEach(apartmentLabelService::saveOrUpdate);

        //3-4 保存图片信息

        /*
        List<GraphInfo> graphInfoList = new ArrayList<>();
        apartmentSubmitVo.getGraphVoList().forEach(graphVo -> {
            GraphInfo graphInfo = new GraphInfo();
            BeanUtils.copyProperties(graphVo,graphInfo);
            graphInfo.setItemType(ItemType.APARTMENT);
            graphInfo.setItemId(apartmentId);
        });
        graphInfoService.saveOrUpdateBatch(graphInfoList);
         */

        /*
        graphInfoService.saveOrUpdateBatch(
            apartmentSubmitVo.getGraphVoList().stream().map(graphVo -> {
                GraphInfo graphInfo = new GraphInfo();
                BeanUtils.copyProperties(graphVo,graphInfo);
                graphInfo.setItemType(ItemType.APARTMENT);
                graphInfo.setItemId(apartmentId);
                return graphInfo;
            }).collect(Collectors.toList())
        );
         */

        graphInfoService.saveOrUpdateBatch(
                apartmentSubmitVo.getGraphVoList().stream().map(graphVo ->
                    GraphInfo.builder()
                            .name(graphVo.getName())
                            .url(graphVo.getUrl())
                            .itemType(ItemType.APARTMENT)
                            .itemId(apartmentId).build()
                ).collect(Collectors.toList())
        );
    }

    @Override
    public void pageApartmentItemByQuery(IPage<ApartmentItemVo> page, ApartmentQueryVo queryVo) {
        apartmentInfoMapper.pageApartmentItemByQuery(page,queryVo);
    }
}




