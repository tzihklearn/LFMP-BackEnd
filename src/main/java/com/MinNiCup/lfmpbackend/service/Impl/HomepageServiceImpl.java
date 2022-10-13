package com.MinNiCup.lfmpbackend.service.Impl;

import com.MinNiCup.lfmpbackend.mapper.UserInfoMapper;
import com.MinNiCup.lfmpbackend.pojo.CommonResult;
import com.MinNiCup.lfmpbackend.pojo.domain.UserInfo;
import com.MinNiCup.lfmpbackend.pojo.dto.result.LawyerResult;
import com.MinNiCup.lfmpbackend.service.HomepageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tzih
 * @date 2022.10.13
 */
@Service
public class HomepageServiceImpl implements HomepageService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public CommonResult<List<LawyerResult>> getLawyerList() {

        List<UserInfo> lawyerList = userInfoMapper.selectList(new QueryWrapper<UserInfo>().eq("isLawyer", 1));

        List<LawyerResult> results = new ArrayList<>();

        for (UserInfo userInfo : lawyerList) {
            results.add(new LawyerResult(userInfo.getUserId(), userInfo.getName(), userInfo.getIntro()));
        }

        return CommonResult.success(results);
    }

    @Override
    public CommonResult<List<LawyerResult>> lawyerSearch(String keyWord) {

        List<UserInfo> lawyerList = userInfoMapper.selectList(
                new QueryWrapper<UserInfo>().eq("isLawyer", 1).like("name", keyWord));

        List<LawyerResult> results = new ArrayList<>();

        for (UserInfo userInfo : lawyerList) {
            results.add(new LawyerResult(userInfo.getUserId(), userInfo.getName(), userInfo.getIntro()));
        }

        return CommonResult.success(results);
    }

}
