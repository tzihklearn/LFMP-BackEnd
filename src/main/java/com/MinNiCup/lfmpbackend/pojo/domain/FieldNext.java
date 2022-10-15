package com.MinNiCup.lfmpbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author tzih
 * @date 2022.10.14
 */
@Data
public class FieldNext {

    @TableId(type = IdType.AUTO)
    Integer id;

    Integer fieldId;

    String fieldSecond;

}
