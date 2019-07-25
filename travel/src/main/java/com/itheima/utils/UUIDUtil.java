package com.itheima.utils;

import java.util.UUID;

public class UUIDUtil {
    public static String getUuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
