package com.labelwall.util;

import java.util.UUID;

/**
 * Created by Administrator on 2017-12-02.
 */
public class Uuid {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
