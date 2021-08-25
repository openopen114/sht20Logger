package com.openopen.dao;

import com.openopen.model.SHT20MST01;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface SHT20MST01Mapper {
    int insert(SHT20MST01 record);

    int insertSelective(SHT20MST01 record);
}