package com.MinNiCup.lfmpbackend.service.Impl;

import com.MinNiCup.lfmpbackend.mapper.ExampleMapper;
import com.MinNiCup.lfmpbackend.mapper.FieldMapper;
import com.MinNiCup.lfmpbackend.mapper.FieldNextMapper;
import com.MinNiCup.lfmpbackend.mapper.UserInfoMapper;
import com.MinNiCup.lfmpbackend.pojo.CommonResult;
import com.MinNiCup.lfmpbackend.pojo.domain.Example;
import com.MinNiCup.lfmpbackend.pojo.domain.Field;
import com.MinNiCup.lfmpbackend.pojo.domain.FieldNext;
import com.MinNiCup.lfmpbackend.pojo.domain.UserInfo;
import com.MinNiCup.lfmpbackend.pojo.dto.param.CaseParam;
import com.MinNiCup.lfmpbackend.pojo.dto.param.CaseSearchParam;
import com.MinNiCup.lfmpbackend.pojo.dto.result.*;
import com.MinNiCup.lfmpbackend.service.HomepageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tzih
 * @date 2022.10.13
 */
@Service
@Slf4j
public class HomepageServiceImpl implements HomepageService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private FieldMapper fieldMapper;

    @Resource
    private FieldNextMapper fieldNextMapper;

    @Resource
    private ExampleMapper exampleMapper;

    @Override
    public CommonResult<LawyerListResult> getLawyerList(Integer page) {

        List<UserInfo> lawyerList = userInfoMapper.selectList(
                new QueryWrapper<UserInfo>().eq("is_lawyer", 1).last("limit " + page+ "," + 5));

        List<LawyerResult> lawyers = new ArrayList<>();

        for (UserInfo userInfo : lawyerList) {
            lawyers.add(new LawyerResult(userInfo.getUserId(), userInfo.getName(), userInfo.getAvatarUrl(), userInfo.getIntro()));
        }

        LawyerListResult results = new LawyerListResult();

        results.setLawyerResults(lawyers);
        results.setPage(page + 5);

        return CommonResult.success(results);
    }

    @Override
    public CommonResult<LawyerListResult> lawyerSearch(String keyWord, Integer page) {

        List<UserInfo> lawyerList = userInfoMapper.selectList(
                new QueryWrapper<UserInfo>().eq("is_lawyer", 1).like("name", keyWord)
                        .last("limit " + page + "," + 5));

        List<LawyerResult> lawyers = new ArrayList<>();

        for (UserInfo userInfo : lawyerList) {
            lawyers.add(new LawyerResult(userInfo.getUserId(), userInfo.getName(), userInfo.getAvatarUrl(), userInfo.getIntro()));
        }

        LawyerListResult results = new LawyerListResult();

        results.setLawyerResults(lawyers);
        results.setPage(page + 5);

        return CommonResult.success(results);
    }

    @Override
    public CommonResult<LawyerDetailsResult> lawyerDetails(Integer lawyerId) {

        UserInfo userInfo = userInfoMapper.selectOne(
                new QueryWrapper<UserInfo>().eq("user_id", lawyerId).eq("is_lawyer", 1));

        if (userInfo == null) {
            return CommonResult.fail("律师不存在");
        }

        Field fieldFirst = fieldMapper.selectOne(
                new QueryWrapper<Field>().select("field_first").eq("id", userInfo.getRealmId()));
        if (fieldFirst == null) {
            log.warn("user_info表realm_id数据异常：" + userInfo.getUserId());
            return CommonResult.fail("查询错误");
        }

        Field fieldSecond = fieldMapper.selectOne(
                new QueryWrapper<Field>().select("field_first").eq("id", userInfo.getViceRealmId()));

        if (fieldSecond == null) {
            log.warn("user_info表vice_realm_id数据异常：" + userInfo.getUserId());
            return CommonResult.fail("查询错误");
        }

        LawyerDetailsResult result = new LawyerDetailsResult();

        result.setLawyerId(userInfo.getUserId());
        result.setName(userInfo.getName());
        result.setAvatarUrl(userInfo.getAvatarUrl());
        result.setYear(userInfo.getYear());
        result.setPhone(userInfo.getPhone());
        result.setAddress(userInfo.getAddress());
        result.setIntro(userInfo.getIntro());
        result.setRealm(fieldFirst.getFieldFirst());
        result.setViceRealm(fieldSecond.getFieldFirst());

        return CommonResult.success(result);
    }

    @Override
    public CommonResult<CaseListResult> caseList(CaseParam caseParam) {

        CaseListResult result = new CaseListResult();

        List<CaseResult> caseList = new ArrayList<>();

        result.setCaseList(caseList);

        Map<String, Object> map = new HashMap<>();

        if (caseParam.getFieldFirst() != null) {
            Field field = fieldMapper.selectOne(new QueryWrapper<Field>().select("id").eq("field_first", caseParam.getFieldFirst()));
            if (field != null) {
                map.put("field_id", field.getId());
            }
            else {
                return CommonResult.success(result);
            }
        }
        if (caseParam.getFieldSecond() != null) {
            FieldNext fieldNext = fieldNextMapper.selectOne(
                    new QueryWrapper<FieldNext>().select("id").eq("field_second", caseParam.getFieldSecond()));
            if (fieldNext != null) {
                map.put("field_next_id", fieldNext.getId());
            }
            else {
                return CommonResult.success(result);
            }
        }

        List<Example> examples = exampleMapper.selectList(new QueryWrapper<Example>().allEq(map));


        for (Example aExample : examples) {
            caseList.add(new CaseResult(aExample.getId(), aExample.getTitle(), aExample.getGuid(), aExample.getCoverUrl()));
        }

        result.setCaseList(caseList);

        return CommonResult.success(result);
    }

    @Override
    public CommonResult<CaseListResult> caseSearch(CaseSearchParam caseSearchParam) {

        CaseListResult result = new CaseListResult();

        List<CaseResult> caseList = new ArrayList<>();

        result.setCaseList(caseList);

        List<Example> examples = exampleMapper.selectList(new QueryWrapper<Example>()
                .and(i -> i.like("title", caseSearchParam.getKeyword()).or().like("guid", caseSearchParam.getKeyword())));

        for (Example aExample : examples) {
            caseList.add(new CaseResult(aExample.getId(), aExample.getTitle(), aExample.getGuid(), aExample.getCoverUrl()));
        }

        result.setCaseList(caseList);

        return CommonResult.success(result);

    }

    @Override
    public CommonResult<CaseDetailsResult> caseDetails(Integer caseId) {

        Example example = exampleMapper.selectOne(new QueryWrapper<Example>().eq("id", caseId));

        UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("user_id", example.getLawyerId()));

        CaseDetailsResult result = new CaseDetailsResult();

        result.setGuid(example.getGuid());
        result.setTitleFirst(example.getTitleFirst());
        result.setTextFirst(example.getTextFirst());
        result.setTitleSecond(example.getTitleSecond());
        result.setTextSecond(example.getTextSecond());
        result.setSolveUrl(example.getSolveUrl());
        result.setAvatarUrl(userInfo.getAvatarUrl());
        result.setLawyerName(userInfo.getName());
        result.setPhone(userInfo.getPhone());

        return CommonResult.success(result);
    }
}
