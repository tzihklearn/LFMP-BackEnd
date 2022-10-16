package com.MinNiCup.lfmpbackend.pojo.dto.param;

import lombok.Data;

import java.util.List;

/**
 * @author tzih
 * @date 2022.10.16
 */
@Data
public class FieldParam {

    String text;

    String tip;

    List<ViceFieldParam> solvingWays;

}
