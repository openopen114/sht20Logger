package com.openopen.job;

import com.openopen.modbus4j.util.SerialPortWrapperImpl;
import com.openopen.model.SHT20MST01;
import com.openopen.service.SHT20MST01Service;
import com.openopen.utils.Moment;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.msg.*;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import lombok.SneakyThrows;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;

@Component
public class SHT20MST01Job implements Job {

    //Logger
    private static final Logger logger = LoggerFactory.getLogger(SHT20MST01Job.class);


    //非controller普通類中使用@Autowired註解 
    public static SHT20MST01Job sht20mst01Job;

    //用於在依賴關係注入完成之後需要執行的方法上，以執行任何初始化
    @PostConstruct
    public void init() {
        sht20mst01Job = this;
    }

    // Modbus Master
    static ModbusMaster master = null;

    //Modbus wrapper
    static SerialPortWrapperImpl wrapper = null;


    // Autowired Mapper
    @Autowired
    private SHT20MST01Service sht20mst01Service;

    //工具類-Moment
    Moment moment = new Moment();


    @SneakyThrows
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//設定日期格式

        logger.info("===> SHT20MST01Job" + df.format(new Date()));

        // print serial port list
        printSerialProtList();


        // Modbus 初始化 連接
        initModbusConnect();

        // salve id
        int slaveId = 1;

        //讀取溫度
        ReadInputRegistersResponse response = ReadInputRegistersRequest(master, slaveId, 1, 2);

        System.out.println("===> 溫度:" + (double) response.getShortData()[0] / 10);
        System.out.println("===> 濕度:" + (double) response.getShortData()[1] / 10);

        //準備 model
        SHT20MST01 model = new SHT20MST01();
        model.setTemperature((new BigDecimal("" + (double) response.getShortData()[0] / 10)));
        model.setHumidity((new BigDecimal("" + (double) response.getShortData()[1] / 10)));
        model.setDateCreate(moment.getSysdate());

        //塞資料到 DB
        sht20mst01Job.sht20mst01Service.insertSht20mst01Model(model);

        //關閉串口
        wrapper.close();


    }


    /*
     *  print serial port 清單
     *
     * */
    public static void printSerialProtList() {
        Enumeration ports = CommPortIdentifier.getPortIdentifiers();

        while (ports.hasMoreElements()) {
            CommPortIdentifier port = (CommPortIdentifier) ports.nextElement();
            System.out.println("===>" + port.getName());
        }
    }


    /*
     *
     * Modbus 初始化 連接
     *
     * */
    public static void initModbusConnect() throws ModbusInitException {

        //9600 8N1 連接
        wrapper = new SerialPortWrapperImpl("/dev/tty.usbserial-AG0KA7Z1", 9600,
                SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE, 0, 0);
        ModbusFactory modbusFactory = new ModbusFactory();
        master = modbusFactory.createRtuMaster(wrapper);

        SerialPort serialPort = wrapper.getSerialPort();
        if (serialPort != null) {
            serialPort.close();
            logger.warn("===> 串口 not null {} 關閉", serialPort.getName());
        }

        master.init();


    }


    /*
     *
     * 讀取保存寄存器 (功能碼 03)
     *
     * */
    private static ReadHoldingRegistersResponse readHoldingRegisters(ModbusMaster master, int slaveId, int start, int len) throws Exception {
        ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, start, len);
        ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) master.send(request);
        if (response.isException()) {
            System.out.println("===> readHoldingRegisters ERROR " + response.getExceptionMessage());
        } else {
            System.out.println("===> readHoldingRegisters " + Arrays.toString(response.getShortData()));
        }


        return response;
    }


    /*
     *
     * 讀取輸入寄存器 (功能碼 04)
     *
     * */
    private static ReadInputRegistersResponse ReadInputRegistersRequest(ModbusMaster master, int slaveId, int start, int len) throws Exception {
        ReadInputRegistersRequest request = new ReadInputRegistersRequest(slaveId, start, len);
        ReadInputRegistersResponse response = (ReadInputRegistersResponse) master.send(request);
        if (response.isException()) {
            System.out.println("===> ReadInputRegistersRequest ERROR " + response.getExceptionMessage());
        } else {


            System.out.println("===> ReadInputRegistersRequest " + Arrays.toString(response.getShortData()));
        }

        return response;
    }


    /*
     *
     * 寫入寄存器 (功能碼 06)
     *
     * */
    private static void writeRegister(ModbusMaster master, int slaveId, int offset, int value) throws Exception {
        WriteRegisterRequest request = new WriteRegisterRequest(slaveId, offset, value);
        WriteRegisterResponse response = (WriteRegisterResponse) master.send(request);
        if (response.isException()) {
            System.out.println("===> WriteRegisterRequest ERROR " + response.getExceptionMessage());
        } else {
            System.out.println("===> WriteRegisterRequest SUCCESS ");
        }
    }


}
