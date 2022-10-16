package com.MinNiCup.lfmpbackend.service.Impl;

import com.MinNiCup.lfmpbackend.mapper.ConsultMapper;
import com.MinNiCup.lfmpbackend.mapper.FieldMapper;
import com.MinNiCup.lfmpbackend.mapper.FieldNextMapper;
import com.MinNiCup.lfmpbackend.pojo.CommonResult;
import com.MinNiCup.lfmpbackend.pojo.domain.Field;
import com.MinNiCup.lfmpbackend.pojo.domain.FieldNext;
import com.MinNiCup.lfmpbackend.pojo.dto.param.FieldParam;
import com.MinNiCup.lfmpbackend.pojo.dto.param.ViceFieldParam;
import com.MinNiCup.lfmpbackend.service.TextService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author tzih
 * @date 2022.10.16
 */
@Service
public class TextServiceImpl implements TextService {

    @Resource
    private FieldMapper fieldMapper;

    @Resource
    private FieldNextMapper fieldNextMapper;

    @Resource
    private ConsultMapper consultMapper;

    @Override
    public CommonResult<String> field(List<FieldParam> fieldParams) {

        System.out.println(Arrays.toString(fieldParams.toArray()));

        for (FieldParam fieldParam : fieldParams) {
            System.out.println(fieldParam.getText());
            Integer fieldId = fieldMapper.selectIdByFieldFirst(fieldParam.getText());
            for (ViceFieldParam solvingWay : fieldParam.getSolvingWays()) {
                FieldNext fieldNext = new FieldNext();

                fieldNext.setFieldId(fieldId);
                fieldNext.setFieldSecond(solvingWay.getText());

                fieldNextMapper.insert(fieldNext);
            }

        }

        return null;
    }
}
