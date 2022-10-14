package com.MinNiCup.lfmpbackend.service.Impl;

import com.MinNiCup.lfmpbackend.lnterceptor.CurrentUser;
import com.MinNiCup.lfmpbackend.lnterceptor.CurrentUserUtil;
import com.MinNiCup.lfmpbackend.mapper.ConsultMapper;
import com.MinNiCup.lfmpbackend.mapper.FieldMapper;
import com.MinNiCup.lfmpbackend.mapper.UserInfoMapper;
import com.MinNiCup.lfmpbackend.pojo.CommonResult;
import com.MinNiCup.lfmpbackend.pojo.domain.Consult;
import com.MinNiCup.lfmpbackend.pojo.domain.Field;
import com.MinNiCup.lfmpbackend.pojo.domain.UserInfo;
import com.MinNiCup.lfmpbackend.pojo.dto.param.LawyerInformationParam;
import com.MinNiCup.lfmpbackend.pojo.dto.param.ReviewFreeConsultParam;
import com.MinNiCup.lfmpbackend.pojo.dto.result.LawyerAvatarResult;
import com.MinNiCup.lfmpbackend.pojo.dto.result.LawyerConsultResult;
import com.MinNiCup.lfmpbackend.pojo.dto.result.LawyerInformationResult;
import com.MinNiCup.lfmpbackend.service.LawyerService;
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
public class LawyerServiceImpl implements LawyerService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private FieldMapper fieldMapper;

    @Resource
    private ConsultMapper consultMapper;

    @Override
    public CommonResult<LawyerInformationResult> check() {

        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();

        UserInfo userInfo = userInfoMapper.selectById(currentUser.getId());

        Field fieldFirst = fieldMapper.selectOne(new QueryWrapper<Field>().select("field_first").eq("id", userInfo.getRealmId()));

        LawyerInformationResult result = new LawyerInformationResult();

        result.setName(userInfo.getName());
        result.setYear(userInfo.getYear());
        result.setPhone(userInfo.getPhone());
        result.setAddress(userInfo.getAddress());
        result.setIntro(userInfo.getIntro());
        result.setRealm(fieldFirst.getFieldFirst());

        return CommonResult.success(result);
    }

    @Override
    public CommonResult<String> modify(LawyerInformationParam lawyerInformationParam) {

        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();

        Field field = fieldMapper.selectOne(
                new QueryWrapper<Field>().select("id").eq("field_first", lawyerInformationParam.getRealm()));

        int update = userInfoMapper.update(null,
                new UpdateWrapper<UserInfo>().set("name", lawyerInformationParam.getName())
                        .set("phone", lawyerInformationParam.getPhone())
                        .set("realm_id", field.getId())
                        .set("address", lawyerInformationParam.getAddress())
                        .set("year", lawyerInformationParam.getYear())
                        .set("intro", lawyerInformationParam.getIntro()).eq("user_id", currentUser.getId()));

        if (update != 1) {
            return CommonResult.fail("更新失败");
        }

        return CommonResult.success("更新成功");
    }

    @Override
    public CommonResult<LawyerAvatarResult> checkAvatar() {

        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();

        UserInfo userInfo = userInfoMapper.selectOne(new
                QueryWrapper<UserInfo>().select("avatar_url").eq("user_id", currentUser.getId()));

        LawyerAvatarResult result = new LawyerAvatarResult();

        result.setAvatarUrl(userInfo.getAvatarUrl());

        return CommonResult.success(result);
    }

    @Override
    public CommonResult<List<LawyerConsultResult>> freeConsult() {

        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();

        List<LawyerConsultResult> results = consultMapper.selectLawyerConsultByLawyer(currentUser.getId(), 1);

        return CommonResult.success(results);
    }

    @Override
    public CommonResult<List<LawyerConsultResult>> onlineConsult() {

        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();

        List<LawyerConsultResult> results = consultMapper.selectLawyerConsultByLawyer(currentUser.getId(), 2);

        return CommonResult.success(results);
    }

    @Override
    public CommonResult<List<LawyerConsultResult>> phoneConsult() {

        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();

        List<LawyerConsultResult> results = consultMapper.selectLawyerConsultByLawyer(currentUser.getId(), 3);

        return CommonResult.success(results);
    }

    @Override
    public CommonResult<String> reviewFreeConsult(ReviewFreeConsultParam reviewFreeConsultParam) {

        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();

        int update = consultMapper.update(null, new UpdateWrapper<Consult>()
                .set("review_data", reviewFreeConsultParam.getReviewData())
                .set("is_reply", 2)
                .eq("id", reviewFreeConsultParam.getConsultId()).eq("lawyer_id", currentUser.getId()));

        if (update != 1) {
            return CommonResult.fail("回复失败");
        }

        return CommonResult.success("回复成功");
    }

}
