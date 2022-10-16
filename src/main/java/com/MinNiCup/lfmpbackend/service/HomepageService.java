package com.MinNiCup.lfmpbackend.service;

import com.MinNiCup.lfmpbackend.pojo.dto.CommonResult;
import com.MinNiCup.lfmpbackend.pojo.dto.param.CaseParam;
import com.MinNiCup.lfmpbackend.pojo.dto.param.CaseSearchParam;
import com.MinNiCup.lfmpbackend.pojo.dto.result.*;

/**
 * @author tzih
 * @date 2022.10.13
 */
public interface HomepageService {

    CommonResult<LawyerListResult> getLawyerList(Integer page);

    CommonResult<LawyerListResult> lawyerSearch(String keyWord, Integer page);

    CommonResult<LawyerDetailsResult> lawyerDetails(Integer lawyerId);

    CommonResult<CaseListResult> caseList(CaseParam caseParam);

    CommonResult<CaseListResult> caseSearch(CaseSearchParam caseSearchParam);

    CommonResult<CaseDetailsResult> caseDetails(Integer caseId);

}
