package com.openopen.service;

import com.openopen.dao.PersonMapper;
import com.openopen.dao.SHT20MST01Mapper;
import com.openopen.model.Person;
import com.openopen.model.SHT20MST01;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SHT20MST01Service {

    // Logger
    private Logger logger = LoggerFactory.getLogger(SHT20MST01Service.class);

    // Autowired Mapper
    @Autowired
    private SHT20MST01Mapper sht20mst01Mapper;


    /*
     *
     * 新增 model
     *
     * */
    public void insertSht20mst01Model(SHT20MST01 _model) {
        sht20mst01Mapper.insertSelective(_model);

    }

}
