package com.MinNiCup.lfmpbackend.controller;

import com.MinNiCup.lfmpbackend.pojo.CommonResult;
import com.MinNiCup.lfmpbackend.pojo.dto.param.FieldParam;
import com.MinNiCup.lfmpbackend.service.TextService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tzih
 * @date 2022.10.16
 */
@RestController
@RequestMapping("/text")
public class TextController {

    @Resource
    private TextService textService;

    @PostMapping("/field")
    public CommonResult<String> field(@RequestBody List<FieldParam> fieldParams) {
        return  textService.field(fieldParams);
    }

}
