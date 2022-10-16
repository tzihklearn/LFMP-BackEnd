package com.MinNiCup.lfmpbackend.service.Impl;

import com.MinNiCup.lfmpbackend.lnterceptor.CurrentUser;
import com.MinNiCup.lfmpbackend.lnterceptor.CurrentUserUtil;
import com.MinNiCup.lfmpbackend.mapper.ConsultMapper;
import com.MinNiCup.lfmpbackend.mapper.UserInfoMapper;
import com.MinNiCup.lfmpbackend.pojo.dto.CommonResult;
import com.MinNiCup.lfmpbackend.pojo.domain.Consult;
import com.MinNiCup.lfmpbackend.pojo.domain.UserInfo;
import com.MinNiCup.lfmpbackend.pojo.dto.param.CommitConsultParam;
import com.MinNiCup.lfmpbackend.pojo.dto.param.ModifyNameParam;
import com.MinNiCup.lfmpbackend.pojo.dto.result.ConsumerNameResult;
import com.MinNiCup.lfmpbackend.pojo.dto.result.ConsumerConsultResult;
import com.MinNiCup.lfmpbackend.service.ConsumerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public CommonResult<ConsumerNameResult> check() {

        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();

        UserInfo userInfo = userInfoMapper.selectOne(
                new QueryWrapper<UserInfo>().select("name").eq("user_id", currentUser.getId()));

        ConsumerNameResult result = new ConsumerNameResult();

        result.setName(userInfo.getName());

        return CommonResult.success(result);
    }

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
    public CommonResult<List<ConsumerConsultResult>> freeConsult() {

        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();

        List<ConsumerConsultResult> results = consultMapper.selectConsumerConsultByConsumer(currentUser.getId(), 1);

        return CommonResult.success(results);
    }

    @Override
    public CommonResult<List<ConsumerConsultResult>> onlineConsult() {

        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();

        List<ConsumerConsultResult> results = consultMapper.selectConsumerConsultByConsumer(currentUser.getId(), 2);

        return CommonResult.success(results);
    }

    @Override
    public CommonResult<List<ConsumerConsultResult>> phoneConsult() {

        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();

        List<ConsumerConsultResult> results = consultMapper.selectConsumerConsultByConsumer(currentUser.getId(), 3);

        return CommonResult.success(results);

    }

    @Override
    public CommonResult<String> commitConsult(CommitConsultParam commitConsultParam) {

        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();

        Consult consult = new Consult();

        consult.setConsumerId(currentUser.getId());
        consult.setLawyerId(commitConsultParam.getLawyerId());
        if (commitConsultParam.getData() != null) {
            consult.setData(commitConsultParam.getData());
        }
        consult.setModel(commitConsultParam.getModel());
        consult.setIsReply(0);

        int insert = consultMapper.insert(consult);

        if (insert != 1) {
            return CommonResult.fail("提交咨询失败");
        }

        return CommonResult.success("提交咨询成功");
    }
}
