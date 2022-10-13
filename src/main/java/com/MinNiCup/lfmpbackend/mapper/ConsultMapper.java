package com.MinNiCup.lfmpbackend.mapper;

import com.MinNiCup.lfmpbackend.pojo.domain.Consult;
import com.MinNiCup.lfmpbackend.pojo.dto.result.FreeConsultResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConsultMapper extends BaseMapper<Consult> {

    List<FreeConsultResult> selectFreeConsultByConsumer(Integer consumerId);

}
