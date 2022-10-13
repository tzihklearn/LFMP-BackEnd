package com.MinNiCup.lfmpbackend.controller;

import com.MinNiCup.lfmpbackend.pojo.CommonResult;
import com.MinNiCup.lfmpbackend.pojo.dto.result.LawyerResult;
import com.MinNiCup.lfmpbackend.service.HomepageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
    public CommonResult<List<LawyerResult>> getLawyerList() {
        return homepageService.getLawyerList();
    }

    @GetMapping("/lawyer-keyword")
    public CommonResult<List<LawyerResult>> lawyerSearch(String keyWord) {
        return homepageService.lawyerSearch(keyWord);
    }

//    @GetMapping("/lawyer-details")
//    public CommonResult<>

}
