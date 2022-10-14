package com.MinNiCup.lfmpbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author tzih
 * @date 2022.10.11
 */
@Data
@TableName("user_info")
public class UserInfo {

    @TableId(type = IdType.AUTO)
    Integer userId;

    String loginId;

    String name;

    Integer sex;

    String email;

    String phone;

    String qq;

    String wx;

    Integer realmId;

    Integer viceRealmId;

    String address;

    Integer year;

    String intro;

    String avatarUrl;

    Boolean isLawyer;

}
