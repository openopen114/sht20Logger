package com.openopen.controller;


import com.openopen.service.SHT20MST01Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sht20")
public class SHT20MST01Controller {
    // Logger
    private Logger logger = LoggerFactory.getLogger(SHT20MST01Controller.class);


    @Autowired
    private SHT20MST01Service sht20mst01Service;


}
