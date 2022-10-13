package com.MinNiCup.lfmpbackend.service.impl;

import com.MinNiCup.lfmpbackend.lnterceptor.CurrentUser;
import com.MinNiCup.lfmpbackend.lnterceptor.CurrentUserUtil;
import com.MinNiCup.lfmpbackend.mapper.UserInfoMapper;
import com.MinNiCup.lfmpbackend.pojo.CommonResult;
import com.MinNiCup.lfmpbackend.pojo.domain.UserInfo;
import com.MinNiCup.lfmpbackend.pojo.dto.param.ModifyNameParam;
import com.MinNiCup.lfmpbackend.service.ConsumerService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tzih
 * @date 2022.10.13
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Resource
    private UserInfoMapper userInfoMapper;

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
}
