package com.MinNiCup.lfmpbackend.mapper;

import com.MinNiCup.lfmpbackend.pojo.domain.User;
import com.MinNiCup.lfmpbackend.pojo.dto.param.LoginParam;
import com.MinNiCup.lfmpbackend.pojo.dto.result.LoginResult;
import com.MinNiCup.lfmpbackend.pojo.po.UserPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    int insertUser(String account, String password);

    UserPo selectByLogin(@Param("loginParam") LoginParam loginParam);

    User selectAllByAccount(String account);

    LoginResult selectLoginByAccount(String account);

}
