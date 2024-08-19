package com.atguigu.lease.web.admin.controller.apartment;


import com.atguigu.lease.common.result.Result;
import com.atguigu.lease.model.entity.AttrKey;
import com.atguigu.lease.model.entity.AttrValue;
import com.atguigu.lease.web.admin.service.AttrKeyService;
import com.atguigu.lease.web.admin.service.AttrValueService;
import com.atguigu.lease.web.admin.vo.attr.AttrKeyVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Tag(name = "房间属性管理")
@RestController
@RequestMapping("/admin/attr")
public class AttrController {

    @Autowired
    private AttrKeyService attrKeyService;

    @Autowired
    private AttrValueService attrValueService;

    @Operation(summary = "新增或更新属性名称")
    @PostMapping("key/saveOrUpdate")
    public Result saveOrUpdateAttrKey(@RequestBody AttrKey attrKey) {
        attrKeyService.saveOrUpdate(attrKey);
        return Result.ok();
    }

    @Operation(summary = "新增或更新属性值")
    @PostMapping("value/saveOrUpdate")
    public Result saveOrUpdateAttrValue(@RequestBody AttrValue attrValue) {
        attrValueService.saveOrUpdate(attrValue);
        return Result.ok();
    }


    @Operation(summary = "查询全部属性名称和属性值列表")
    @GetMapping("list")
    public Result<List<AttrKeyVo>> listAttrInfo() {
        /**
         * 实现方式一
        //1.先查询所有的名称
        List<AttrKey> attrKeyList = attrKeyService.list();
        List<AttrKeyVo> attrKeyVoList = new ArrayList<>(attrKeyList.size());
        //2.在迭代每一个名称，去查询对应的值列表
        attrKeyList.forEach(attrKey -> {
            List<AttrValue> attrValueList = attrValueService.list(new LambdaQueryWrapper<AttrValue>().eq(AttrValue::getAttrKeyId, attrKey.getId()));
            AttrKeyVo attrKeyVo = new AttrKeyVo();
            BeanUtils.copyProperties(attrKey, attrKeyVo);
            //attrKeyVo.setId(attrKey.getId());
            //attrKeyVo.setName(attrKey.getName());
            attrKeyVo.setAttrValueList(attrValueList);
            attrKeyVoList.add(attrKeyVo);
        });
         */

        /**
         * 实现方式二
         */
        List<AttrKeyVo> attrKeyVoList = attrKeyService.getAttrKeyVoList();
        return Result.ok(attrKeyVoList);

    }

    @Operation(summary = "根据id删除属性名称")
    @DeleteMapping("key/deleteById")
    public Result removeAttrKeyById(@RequestParam Long attrKeyId) {
        attrKeyService.removeById(attrKeyId);
        attrValueService.remove(new LambdaQueryWrapper<AttrValue>().eq(AttrValue::getAttrKeyId,attrKeyId));
        return Result.ok();
    }

    @Operation(summary = "根据id删除属性值")
    @DeleteMapping("value/deleteById")
    public Result removeAttrValueById(@RequestParam Long id) {
        attrValueService.removeById(id);
        return Result.ok();
    }

}
