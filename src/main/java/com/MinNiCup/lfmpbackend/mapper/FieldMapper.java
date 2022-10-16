package com.MinNiCup.lfmpbackend.mapper;

import com.MinNiCup.lfmpbackend.pojo.domain.Field;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FieldMapper extends BaseMapper<Field> {
    Integer selectIdByFieldFirst(String fieldFirst);
}
