package com.MinNiCup.lfmpbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author tzih
 * &#064;date  2022.10.14
 */
@Data
public class Example {

    @TableId(type = IdType.AUTO)
    Integer id;

    Integer fieldId;

    Integer fieldNextId;

    String coverUrl;

    String title;

    String guid;

    String titleFirst;

    String textFirst;

    String titleSecond;

    String textSecond;

    String solveUrl;

    Integer lawyerId;

}
