package com.MinNiCup.lfmpbackend.pojo.dto.result;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author tzih
 * @date 2022.10.14
 */
@Data
@AllArgsConstructor
public class CaseResult {

    Integer caseId;

    String title;

    String guid;

    String coverUrl;

}
