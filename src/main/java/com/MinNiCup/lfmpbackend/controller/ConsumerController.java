package com.MinNiCup.lfmpbackend.controller;

import com.MinNiCup.lfmpbackend.pojo.CommonResult;
import com.MinNiCup.lfmpbackend.pojo.dto.param.ModifyNameParam;
import com.MinNiCup.lfmpbackend.pojo.dto.result.FreeConsultResult;
import com.MinNiCup.lfmpbackend.pojo.dto.result.LawyerResult;
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

    @PostMapping("/modify-name")
    public CommonResult<String> modifyName(@RequestBody ModifyNameParam modifyNameParam) {
        return consumerService.modifyName(modifyNameParam);
    }

    @GetMapping("/free-consult")
    public CommonResult<List<FreeConsultResult>> freeConsult() {
        return consumerService.freeConsult();
    }



}
