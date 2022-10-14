package com.MinNiCup.lfmpbackend.controller;

import com.MinNiCup.lfmpbackend.pojo.CommonResult;
import com.MinNiCup.lfmpbackend.pojo.dto.param.CaseParam;
import com.MinNiCup.lfmpbackend.pojo.dto.param.CaseSearchParam;
import com.MinNiCup.lfmpbackend.pojo.dto.result.*;
import com.MinNiCup.lfmpbackend.service.HomepageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author tzih
 * @date 2022.10.13
 */
@RestController
@RequestMapping("/homepage")
public class HomepageController {

    @Resource
    private HomepageService homepageService;


    @GetMapping("/lawyer-list")
    public CommonResult<LawyerListResult> getLawyerList(@RequestParam Integer page) {
        return homepageService.getLawyerList(page);
    }

    @GetMapping("/lawyer-keyword")
    public CommonResult<LawyerListResult> lawyerSearch(@RequestParam String keyWord, @RequestParam Integer page) {
        return homepageService.lawyerSearch(keyWord, page);
    }

    @GetMapping("/lawyer-details")
    public CommonResult<LawyerDetailsResult> lawyerDetails(@RequestParam Integer lawyerId) {
        return homepageService.lawyerDetails(lawyerId);
    }

    @PutMapping("/case-list")
    public CommonResult<CaseListResult> caseList(@RequestBody CaseParam caseParam) {
        return homepageService.caseList(caseParam);
    }

    @PutMapping("/case-search")
    public CommonResult<CaseListResult> caseSearch(@RequestBody CaseSearchParam caseSearchParam) {
        return homepageService.caseSearch(caseSearchParam);
    }

    @GetMapping("/case-details")
    public CommonResult<CaseDetailsResult> caseDetails(@RequestParam Integer caseId) {
        return homepageService.caseDetails(caseId);
    }

}
