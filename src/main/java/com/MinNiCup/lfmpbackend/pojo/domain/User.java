package com.MinNiCup.lfmpbackend.pojo.domain;

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

    @TableId
    Integer id;

    String account;

    String password;

    Integer idIdent;

}
