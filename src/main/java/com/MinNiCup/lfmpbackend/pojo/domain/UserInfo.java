package com.MinNiCup.lfmpbackend.pojo.domain;

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

    @TableId
    Integer userId;

    String loginId;

    String name;

    Integer sex;

    String email;

    String phone;

    String qq;

    String wx;

    String realmId;

    String address;

    String year;

    String intro;

}
