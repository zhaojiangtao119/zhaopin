package com.labelwall.util;

import java.io.*;
import java.util.UUID;

/**
 * Created by Administrator on 2017-12-02.
 */
public class CommonUtil {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static byte[] getFileByte(File file){
        if(file == null){
            return null;
        }
        try {
            FileInputStream inputStream = new FileInputStream(file);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(4096);
            byte[] b = new byte[4096];
            int n;
            while ((n=inputStream.read(b)) != -1){
                outputStream.write(b,0,n);
            }
            inputStream.close();
            outputStream.close();
            return outputStream.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
