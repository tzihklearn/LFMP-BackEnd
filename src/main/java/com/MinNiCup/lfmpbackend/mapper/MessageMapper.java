package com.MinNiCup.lfmpbackend.mapper;

import com.MinNiCup.lfmpbackend.pojo.domain.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageMapper {
    int insertMessage(Message message);
    List<Message> selectMessageByConsultId(Integer consultId);
}
