package com.MinNiCup.lfmpbackend.controller;

import com.MinNiCup.lfmpbackend.pojo.dto.CommonResult;
import com.MinNiCup.lfmpbackend.pojo.dto.param.LawyerInformationParam;
import com.MinNiCup.lfmpbackend.pojo.dto.param.ReviewFreeConsultParam;
import com.MinNiCup.lfmpbackend.pojo.dto.param.SetPhoneConsultParam;
import com.MinNiCup.lfmpbackend.pojo.dto.result.LawyerAvatarResult;
import com.MinNiCup.lfmpbackend.pojo.dto.result.LawyerConsultResult;
import com.MinNiCup.lfmpbackend.pojo.dto.result.LawyerInformationResult;
import com.MinNiCup.lfmpbackend.service.LawyerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tzih
 * @date 2022.10.13
 */
@RestController
@RequestMapping("/lawyer")
public class LawyerController {

    @Resource
    private LawyerService lawyerService;

    @GetMapping("/check")
    public CommonResult<LawyerInformationResult> check() {
        return lawyerService.check();
    }

    @PostMapping("/modify")
    public CommonResult<String> modify(@RequestBody LawyerInformationParam lawyerInformationParam) {
        return lawyerService.modify(lawyerInformationParam);
    }

    @GetMapping("/check-avatar")
    public CommonResult<LawyerAvatarResult> checkAvatar() {
        return lawyerService.checkAvatar();
    }

    @GetMapping("/free-consult")
    public CommonResult<List<LawyerConsultResult>> freeConsult() {
        return lawyerService.freeConsult();
    }

    @GetMapping("/on-line-consult")
    public CommonResult<List<LawyerConsultResult>> onlineConsult() {
        return lawyerService.onlineConsult();
    }

    @GetMapping("/phone-consult")
    public CommonResult<List<LawyerConsultResult>> phoneConsult() {
        return lawyerService.phoneConsult();
    }

    @PostMapping("/review-free-consult")
    public CommonResult<String> reviewFreeConsult(@RequestBody ReviewFreeConsultParam reviewFreeConsultParam) {
        return lawyerService.reviewFreeConsult(reviewFreeConsultParam);
    }

    @PostMapping("/set-phone-consult")
    public CommonResult<String> setPhoneConsult(@RequestBody SetPhoneConsultParam setPhoneConsultParam) {
        return lawyerService.setPhoneConsult(setPhoneConsultParam);
    }

}
