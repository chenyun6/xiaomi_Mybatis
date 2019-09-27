package com.qf.utils;

public class StringUtils {
    public static boolean stringEqual(String s1,String s2){
        if(!s1.equals(s2)){
            return false;
        }
        return true;
    }
    public static boolean stringJudge(String s){
        if(s==null||s.trim().length()==0){
            return false;
        }
        return true;
    }
}
