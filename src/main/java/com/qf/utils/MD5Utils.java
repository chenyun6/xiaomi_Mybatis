package com.qf.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5Utils {
    //密码加密
    public static String StrongPwd(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            BigInteger bigInteger = new BigInteger(1, digest);
            return bigInteger.toString(16);   //返回16位的字符串
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加密失败！", e);
        }
    }
}
