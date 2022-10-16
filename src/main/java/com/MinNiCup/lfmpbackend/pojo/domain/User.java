package com.MinNiCup.lfmpbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author tzih
 * @date 2022.10.11
 */
@TableName("user")
@Data
public class User {

    @TableId(type = IdType.AUTO)
    Integer id;

    String account;

    String password;

    Integer isIdent;

}
