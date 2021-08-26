package com.openopen.dao;

import com.openopen.model.SHT20MST01;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SHT20MST01Mapper {
    int insert(SHT20MST01 record);

    int insertSelective(SHT20MST01 record);

    int updateStatus(String type);

    List<SHT20MST01> selectByStatus(String status);
}