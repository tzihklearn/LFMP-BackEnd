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
import com.MinNiCup.lfmpbackend.pojo.dto.result.CommitConsultResult;
import com.MinNiCup.lfmpbackend.pojo.dto.result.ConsumerNameResult;
import com.MinNiCup.lfmpbackend.pojo.dto.result.ConsumerConsultResult;
import com.MinNiCup.lfmpbackend.pojo.dto.result.FreeConsumerConsultResult;
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
            return CommonResult.fail("????????????");
        }

        return CommonResult.success("????????????");
    }

    @Override
    public CommonResult<List<FreeConsumerConsultResult>> freeConsult() {

        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();

        List<FreeConsumerConsultResult> results = consultMapper.selectFreeConsumerConsultByConsumer(currentUser.getId(), 1);

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
    public CommonResult<CommitConsultResult> commitConsult(CommitConsultParam commitConsultParam) {

        int isReply = 0;

        if (commitConsultParam.getModel() == 2) {
            isReply = 1;
        }

        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();

        Consult consult = new Consult();

        consult.setConsumerId(currentUser.getId());
        consult.setLawyerId(commitConsultParam.getLawyerId());
        if (commitConsultParam.getData() != null) {
            consult.setData(commitConsultParam.getData());
        }
        consult.setModel(commitConsultParam.getModel());
        consult.setIsReply(isReply);

        int insert = consultMapper.insert(consult);

        if (insert != 1) {
            return CommonResult.fail("??????????????????");
        }

        Consult consultId = consultMapper.selectOne(new QueryWrapper<Consult>().select("id")
                .eq("consumer_id", currentUser.getId())
                .eq("lawyer_id", commitConsultParam.getLawyerId())
                .eq("model", commitConsultParam.getModel())
                .eq("is_reply", isReply)
                .orderByDesc("id")
                .last("limit 1"));

        CommitConsultResult result = new CommitConsultResult();

        result.setConsultId(consultId.getId());

        return CommonResult.success(result);
    }
}
