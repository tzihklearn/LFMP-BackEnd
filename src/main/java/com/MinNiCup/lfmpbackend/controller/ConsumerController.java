package com.MinNiCup.lfmpbackend.controller;

import com.MinNiCup.lfmpbackend.pojo.CommonResult;
import com.MinNiCup.lfmpbackend.pojo.dto.param.CommitConsultParam;
import com.MinNiCup.lfmpbackend.pojo.dto.param.ModifyNameParam;
import com.MinNiCup.lfmpbackend.pojo.dto.result.ConsumerNameResult;
import com.MinNiCup.lfmpbackend.pojo.dto.result.ConsumerConsultResult;
import com.MinNiCup.lfmpbackend.service.ConsumerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tzih
 * @date 2022.10.13
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Resource
    private ConsumerService consumerService;

    @GetMapping("/check")
    public CommonResult<ConsumerNameResult> check() {
        return consumerService.check();
    }

    @PostMapping("/modify-name")
    public CommonResult<String> modifyName(@RequestBody ModifyNameParam modifyNameParam) {
        return consumerService.modifyName(modifyNameParam);
    }

    @GetMapping("/free-consult")
    public CommonResult<List<ConsumerConsultResult>> freeConsult() {
        return consumerService.freeConsult();
    }

    @GetMapping("/on-line-consult")
    public CommonResult<List<ConsumerConsultResult>> onlineConsult() {
        return consumerService.onlineConsult();
    }

    @GetMapping("/phone-consult")
    public CommonResult<List<ConsumerConsultResult>> phoneConsult() {
        return consumerService.phoneConsult();
    }

    @PostMapping("/commit-consult")
    public CommonResult<String> commitConsult(@RequestBody CommitConsultParam commitConsultParam) {
        return consumerService.commitConsult(commitConsultParam);
    }



}
