package com.MinNiCup.lfmpbackend.mapper;

import com.MinNiCup.lfmpbackend.pojo.domain.Consult;
import com.MinNiCup.lfmpbackend.pojo.dto.result.ConsumerConsultResult;
import com.MinNiCup.lfmpbackend.pojo.dto.result.LawyerConsultResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConsultMapper extends BaseMapper<Consult> {

    List<ConsumerConsultResult> selectConsumerConsultByConsumer(Integer consumerId, Integer model);

    List<LawyerConsultResult> selectLawyerConsultByLawyer(Integer lawyerId, Integer model);
    Consult selectByConsultId(Integer id);
}
