package com.qf.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RandomUtils {
    public static String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date = new Date();
        String format = sdf.format(date);
        return format;
    }
    public static String createAction(){
        int i = new Random().nextInt(900)+100;

        return getTime()+Integer.toHexString(i);
    }
    //生成订单号
    public static String createOrderId(){
        return getTime();
    }
}
