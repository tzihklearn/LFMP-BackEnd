package com.MinNiCup.lfmpbackend.service.impl;

import com.MinNiCup.lfmpbackend.lnterceptor.CurrentUser;
import com.MinNiCup.lfmpbackend.lnterceptor.CurrentUserUtil;
import com.MinNiCup.lfmpbackend.mapper.ConsultMapper;
import com.MinNiCup.lfmpbackend.mapper.UserInfoMapper;
import com.MinNiCup.lfmpbackend.pojo.CommonResult;
import com.MinNiCup.lfmpbackend.pojo.domain.UserInfo;
import com.MinNiCup.lfmpbackend.pojo.dto.param.ModifyNameParam;
import com.MinNiCup.lfmpbackend.pojo.dto.result.FreeConsultResult;
import com.MinNiCup.lfmpbackend.pojo.dto.result.LawyerResult;
import com.MinNiCup.lfmpbackend.service.ConsumerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tzih
 * @date 2022.10.13
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private ConsultMapper consultMapper;

    @Override
    public CommonResult<String> modifyName(ModifyNameParam modifyNameParam) {

        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();

        Integer userId = currentUser.getId();

        int update = userInfoMapper.update(null,
                new UpdateWrapper<UserInfo>().set("name", modifyNameParam.getName()).eq("user_id", userId));

        if (update != 1) {
            return CommonResult.fail("更新失败");
        }

        return CommonResult.success("更新成功");
    }

    @Override
    public CommonResult<List<FreeConsultResult>> freeConsult() {

        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();

        List<FreeConsultResult> freeConsultResults = consultMapper.selectFreeConsultByConsumer(currentUser.getId());

        return CommonResult.success(freeConsultResults);
    }

}
