package com.openopen.service;

import com.google.gson.Gson;
import com.openopen.dao.SHT20MST01Mapper;
import com.openopen.model.ActionResult;
import com.openopen.model.SHT20MST01;
import com.openopen.utils.Http;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class SHT20MST01Service {

    // Logger
    private Logger logger = LoggerFactory.getLogger(SHT20MST01Service.class);

    // Autowired Mapper
    @Autowired
    private SHT20MST01Mapper sht20mst01Mapper;

    // 新增SHT20 Model API
    @Value("${API.add_sht20_url}")
    private String add_sht20_url;


    /*
     *
     * 新增 model
     *
     * */
    public void insertSht20mst01Model(SHT20MST01 _model) {
        sht20mst01Mapper.insertSelective(_model);

    }


    /*
     *
     * 將資料送到 Cloud
     *
     *
     * */

    public void transDataListToCloud() throws Exception {
        // NULL_TO_X:處理中
        // X_TO_Y:處理成功
        // X_TO_N:處理失敗

        Gson gson = new Gson();

        // ===== 1. 標註狀態:處理中 =====
        logger.info("===== 1. 標註狀態:處理中 ===== 0 ");
        sht20mst01Mapper.updateStatus("NULL_TO_X");
        logger.info("===== 1. 標註狀態:處理中 ===== 1 ");

        // ===== 2. 抓狀態處理中(X)的資料清單 =====
        logger.info("===== 2. 抓狀態處理中(X)的資料清單 ===== 0 ");
        List<SHT20MST01> datalist = sht20mst01Mapper.selectByStatus("X");

        logger.info("===== 2. 抓狀態處理中(X)的資料清單 ===== 1 " + "SIZE : " + datalist.size());

        if (datalist.size() > 0) {
            // ===== 3. 轉檔到雲端 =====
            logger.info("===== 3. 轉檔到雲端 ===== 0 " + "SIZE : " + datalist.size());
            Http http = new Http();
            logger.info("===> add_sht20_url:" + add_sht20_url);
            String json = gson.toJson(datalist);

            String responseJson = http.sendPost(add_sht20_url, json);
            logger.info("===> response Json");
            logger.info(responseJson);
            ActionResult res = gson.fromJson(responseJson, ActionResult.class);
            logger.info("===== 3. 轉檔到雲端 ===== 1 " + "SIZE : " + datalist.size());

            // ===== 4.標註狀態:處理結果 (成功/失敗) =====

            logger.info("===== 4.標註狀態:處理結果 (成功/失敗) ===== 0 " + "SIZE : " + datalist.size());
            if (Objects.equals(res.getRESULT(), "OK")) {
                //成功
                logger.info("===> 成功");
                sht20mst01Mapper.updateStatus("X_TO_Y");
            } else {
                //失敗
                logger.error("===> 失敗");
                //TODO: 失敗處理 send line notify
                sht20mst01Mapper.updateStatus("X_TO_N");
            }

            logger.info("===== 4.標註狀態:處理結果 (成功/失敗) ===== 1 " + "SIZE : " + datalist.size());


        }


    }


}
